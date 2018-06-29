<template>
  <div class="container">
    <h2 class="title">项目评审管理</h2>
    <div class="head-info-wrapper">
      <div>
        <span>项目管理 > </span>
        <span v-if= "type==='0'">项目评审查看管理</span>
        <span v-if= "type==='1'">项目评审修改管理</span>
      </div>
      <div class="btn-wrap" v-if= "type==='0'">
        <el-button type="primary" size="small"  @click="toUpdate">修改信息</el-button>
      </div>
      <div class="btn-wrap" v-if= "type==='1'">
        <el-button type="primary" plain size="small" @click="cancel">取消修改</el-button>
        <el-button type="warning" plain size="small" @click="affirm">确认修改</el-button>
      </div>
    </div>
    <div class="info-wrapper">
      <div class="message-project">
        <el-row>
          <el-col :span="6">
            <span class="item-name">项目名称</span>
            <div class="item-content">
              <el-input placeholder="请输入项目名称" v-model="project.name" disabled></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">项目负责人</span>
            <div class="item-content">
              <el-input placeholder="请输入项目负责人" v-model="project.leader" disabled></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">指导老师</span>
            <div class="item-content">
              <el-input placeholder="请输入指导老师" v-model="project.counselor" disabled></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">评审人</span>
            <div class="item-content">
              <el-input placeholder="请输入评审专家" v-model="project.reviewer" disabled></el-input>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <span class="item-name">评审阶段</span>
            <div class="item-content">
                <el-select v-model="project.reviewTime" @change="initReviewMsg(project.reviewTime)">
                  <el-option
                    v-for="item in options2"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">评审分数</span>
            <div class="item-content">
              <el-input placeholder="请输入评审人" v-model="project.result" :disabled="isCheck"></el-input>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <span class="item-name">评语</span>
            <div class="item-content">
              <quill-editor v-model="project.reviewMsg" :disabled="isCheck"></quill-editor>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="message-file">
        <div class="message-title">
          <span>附件</span>
        </div>
        <el-row>
          <el-col :span="16">
            <div class="item-content" v-if= "type==='1'">
              <el-upload
                class="upload-demo"
                ref="upload"
                action="/api/jsp/ProjectReviewUploadFiles"
                :on-success="handleSuccess"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :file-list="fileList"
                :data="fromData"
                :auto-upload="false">
                <el-button slot="trigger" size="small" type="primary">选取附件</el-button>
                <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
                <el-button style="margin-left: 10px;"   size="small" type="danger" @click="showAllFile">全部文件</el-button>
                <el-button style="margin-left: 10px;"   size="small" type="danger" @click="deleteAllFile">删除全部</el-button>
                <!-- <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，且不超过500kb</div> -->
              </el-upload>
            </div>
            <div class="item-content" v-if= "type==='0'">
              <el-button type="success" size="mini"  v-if="fileList.length > 0" @click="downloadAll">下载附件</el-button>
              <el-row :gutter="200" class="info-content"  v-if="fileList.length > 0">
                <el-col :span="24" class="info-item">
                  <div class="item-content">
                    <div class="attack-link">
                      <ul v-if="fileList.length > 0" class="download">
                        <li v-for="file in fileList" :key="fileList.fileList" @click="downloadOne">{{ file.name }}</li>
                      </ul>
                    </div>
                  </div>
                </el-col>
              </el-row>
            </div>
            <div class="item-content" v-if="type==='0' && fileList.length == 0">
              暂无文件
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import qs from 'qs'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import { quillEditor } from 'vue-quill-editor'

export default {
  data () {
    return {
      options2: [{
        value: '申请',
        label: '申请'
      }, {
        value: '中期',
        label: '中期',
      },{
        value: '结题',
        label: '结题',
      }],
      pickerOptions1: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
      project: {
        name: "",
        leader: '',
        counselor:'',
        reviewer: '',
        reviewTime: '',
        result: '',
        reviewMsg: '',
      },
      fileList: [
            // {name: '', url: ''}
      ],  
      type: '0',   //0为查看通知；1为修改通知
      isCheck: true,
      isUpload: false,
      proId:'',
    }
  },
  components: {
    quillEditor,
  },
  computed:{
    fromData:function(){
      return {
        id: this.proId,
        type:this.project.reviewTime,
      };
    }
  },
  mounted:function(){
    // 获取路由上的type参数
    this.type = this.$route.params.type;
    this.isCheck = this.type == '0'?true:false
    this.proId =  this.$route.params.proId;
    this.initProRev();
    this.initAllFileList();
  },
  methods: {
    initReviewMsg(init){
      let params = {
        proId:this.$route.params.proId,
        proStatus:init
      }
      console.log(params);
      // console.log(this.project.status);
      axios.post('/api/jsp/FindRevbytime',qs.stringify(params))
      .then(response=>{
        // console.log(response.data.datas);
        // 评审信息
        let reviewMsg = response.data.datas;
        // 评审信息
        if(reviewMsg == undefined){
            this.project.result='';
            this.project.reviewer = '';
            this.project.reviewMsg = '';
        }else{
            this.project.result=reviewMsg.review_code;
            this.project.reviewMsg=reviewMsg.review_msg;
            this.project.reviewer = reviewMsg.review_user_name;
        }
        this.initFileList();
      })
      .catch(err=>{
        console.log(err);
      })

    },
    toUpdate(){
      this.type = '1';
      this.isCheck = this.type == '0'?true:false
      this.$router.push(`/EvaluateManageInfo/1/${this.$route.params.proId}`);
    },
    cancel(){
      this.initProRev();
    },
    affirm(){
      let params ={
        project_id: this.$route.params.proId,
        review_msg: this.project.reviewMsg,
        review_code: this.project.result,
        review_type: this.project.reviewTime,
      }
      console.log(params+"徐欣奕");
      axios.post('/api/jsp/UpdateProReview',qs.stringify(params))
      .then(response=>{
        if(response.data.state=='200'){
          this.$router.push(`/admin/ProjectEvaluateManage`);
        }
      })
      .catch(err=>{
        console.log(err);
      })
    },
    initProRev(){
      let params = {
          proId:this.$route.params.proId
        }
      console.log(params);
      axios.post('/api/jsp/FindSpeProReview',qs.stringify(params))
      .then(response=>{
          let proInfo = response.data.datas;         
          this.project.name = proInfo.proObj.pro_name;
          this.project.leader = proInfo.stuObj.user_name;
          this.project.counselor = proInfo.teaObj.user_name;
          this.project.reviewer = proInfo.prorevObj.review_user_name;
          this.project.reviewTime=proInfo.proObj.pro_status;
          this.project.result = proInfo.prorevObj.review_code;
          this.project.reviewMsg=proInfo.prorevObj.review_msg;
          //console.log(proInfo);
        }).catch(err=>{
            console.log(err);
        });
    },
    // 初始化全部文件列表
    initAllFileList(){
      this.fileList = [];
      let params = {
         id: this.proId,
      }
      axios.post('/api/jsp/ProjectReviewFileQueryAll',qs.stringify(params))
      .then(response=>{
        let fileData = response.data.datas;
        fileData.forEach(element => {
          this.fileList.push({
            name: element.fileName,
            url: element.filePath,
          })
        });
      })
      .catch(err=>{
        console.log(err);
      })
    },
    // 初始化全部文件列表
    initFileList(){
      this.fileList = [];
      let params = {
         id: this.proId,
         type: this.project.reviewTime,
      }
      axios.post('/api/jsp/ProjectReviewFileQueryByType',qs.stringify(params))
      .then(response=>{
        let fileData = response.data.datas;
        fileData.forEach(element => {
          this.fileList.push({
            name: element.fileName,
            url: element.filePath,
          })
        });
        console.log(response)
      })
      .catch(err=>{
        console.log(err);
      })
    },
    handleSuccess(res,file,fileList){
      // this.fromData.id = res.datas;
      console.log(res)
    },
    submitUpload() {
      console.log(this.fromData);
      this.isUpload = true;
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log()
      let params = {
        id: this.proId,
        path: file.url,
      }
      axios.post('/api/jsp/ProjectReviewFileDeleteOne',qs.stringify(params))
      .then(response=>{
        console.log(response)
      })
    },
    handlePreview(file){
      console.log(file);
    },
    // 显示全部文件
    showAllFile(){
      this.initAllFileList();
    },
    // 下载所有文件
    downloadAll(){  
      window.open("http://localhost:8080/jsp/ProjectReviewFileDownload?type=all&id="+this.proId);
    },
    // 下载某个阶段的文件
    downloadOne(){},
    // 删除全部文件
    deleteAllFile(){
      let params = {
        id:this.proId
      }
      axios.post('/api/jsp/ProjectReviewFileDeleteAll',qs.stringify(params))
      .then(response=>{
        console.log(response)
        if(response.data.state==200){
          this.fileList = [];
        }
      })
    },
  }
}
</script>

<style scoped>
.container{
  width:90%;
  margin: auto;
  padding: 10px 0;
  min-width: 1350px;
  text-align: left;
}
.title{
  text-align: center;
  padding: 0 0 20px 0;
  margin: 0;
  border-bottom: 5px solid #cbcbcb;
}
.head-info-wrapper{
  display: flex;
  align-items:center;
  justify-content: space-between;
  padding: 10px 0;
  font-size: 16px;
}
.info-wrapper{
  margin: auto;
  padding: 20px 3%;
  background-color: #ffffff;
}
.el-row {
  margin-bottom: 20px;
}
.el-row:last-child{
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}
.message-file{
  margin: 30px 0 0 0;
}
.message-title{
  margin: 15px 0;
  padding: 10px 0;
  font-size: 16px;
  border-bottom: 1px solid gray;
}
.item-name{
  font-size: 16px;
}
.item-content{
  margin-top: 15px;
}
.el-input,.el-select,.el-date-picker{
  width:85%;
}
.quill-editor{
  width: 95%;
}
</style>
