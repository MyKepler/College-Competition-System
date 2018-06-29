<template>
  <div class="container">
    <h2 class="title">项目中期管理</h2>
    <div class="head-info-wrapper">
      <div>
        <span>项目管理 > </span>
        <span v-if= "type==='0'">项目中期查看管理</span>
        <span v-if= "type==='1'">项目中期修改管理</span>
      </div>
      <div class="btn-wrap" v-if= "type==='0'">
        <el-button type="primary" size="small" @click="toUpdate">修改信息</el-button>
        <!-- <el-button type="danger" size="small" @click="toDelete">删除信息</el-button> -->
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
            <el-input placeholder="请输入项目名称" v-model="project.name" :disabled="isCheck"></el-input>
          </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">提交状态</span>
            <div class="item-content">
                <el-select v-model="project.submitStatus" disabled>
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
            <span class="item-name">材料提交时间</span>
            <div class="item-content">
              <el-date-picker
                :disabled="isCheck"
                v-model="project.submitTime"
                type="date"
                value-format="yyyy-MM-dd"
                :picker-options="pickerOptions1" disabled>
              </el-date-picker>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">负责人</span>
            <div class="item-content">
              <el-input placeholder="请输入负责人" v-model="project.leader" :disabled="isCheck"></el-input>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <span class="item-name">指导老师</span>
            <div class="item-content">
              <el-input placeholder="请输入指导老师" v-model="project.counselor" :disabled="isCheck"></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">评审人</span>
            <div class="item-content">
              <el-input placeholder="请输入评审任" v-model="review.userId" disabled></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">评审人姓名</span>
            <div class="item-content">
              <el-input placeholder="请输入评审任" v-model="review.name" disabled></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">评审分数</span>
            <div class="item-content">
              <el-input placeholder="评审结果" v-model="review.reviewScode" disabled></el-input>
            </div>
          </el-col>
        </el-row>
      </div>
      <!-- <div class="message-submitUser">
        <div class="message-wrapper-item">
          <span class="position">材料提交人</span>
          <el-button class="btn-check" type="primary" size="small">详情查看</el-button>
          <div class="itemshow">
            <el-row>
              <el-col :span="6">
                <span class="item-name">学号</span>
                <div class="item-content">
                  <el-input disabled v-model="user.userId"></el-input>
                </div>
              </el-col>
              <el-col :span="6">
                <span class="item-name">姓名</span>
                <div class="item-content">
                  <el-input disabled v-model="user.name"></el-input>
                </div>
              </el-col>
              <el-col :span="6">
                <span class="item-name">邮箱</span>
                <div class="item-content">
                  <el-input disabled v-model="user.email"></el-input>
                </div>
              </el-col>
              <el-col :span="6">
                <span class="item-name">手机号</span>
                <div class="item-content">
                  <el-input disabled v-model="user.userPhone"></el-input>
                </div>
              </el-col>
            </el-row>
          </div>
        </div>
      </div> -->
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
                action="/api/jsp/ProjectSignUpFileUpload"
                :on-preview="handlePreview"
                :on-remove="handleRemove"
                :on-success="handleSuccess"
                :file-list="fileList"
                :data="fromData"
                :auto-upload="false">
                <el-button slot="trigger" size="small" type="primary">选取附件</el-button>
                <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
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
import util,{ getDate } from './../../../util/util.js'
export default {
  data () {
    return {
      pickerOptions1: {
        disabledDate(time) {
          // return time.getTime() > Date.now();
        }
      },
      project: {
        name:'',
        submitStatus: '',
        submitTime: '',
        protime:'',
        leader: '',
        counselor:'',
      },
      review: {
        userId: '',
        name: '',
        reviewScode: '',
      },
      options2: [{
        value: '1',
        label: '1'
      }, {
        value: '2',
        label: '2',
      }],
      fileList: [  
        // {name: '', url: ''}
      ],
      type: '0',   //0为查看通知；1为修改通知
      isCheck: true,
      isUpload: false,
      proId: '',
    }
  },
  computed:{
    fromData:function(){
      return {
        id: this.proId,
        time: getDate(),
        state:'可用',
        type:'中期',
      };
    }
  },
  mounted:function(){
    // 获取路由上的type参数
    this.type = this.$route.params.type;
    this.proId = this.$route.params.proId;
    this.isCheck = this.type == '0'?true:false
    this.initMidProView();
    this.initFileList();
  },
  methods: {
    // 跳转到编辑页面
    toUpdate(){
      this.type = '1';
      this.isCheck = this.type == '0'?true:false
      this.$router.push(`/MidManageInfo/1/${this.$route.params.proId}`);
    },
    // 取消修改
    cancel(){
      this.initMidProView();
    },
    // 确认修改
    affirm(){
      let params ={
        proId: this.$route.params.proId,
        proName: this.project.name,
        proStatus: this.project.protime,
        leader: this.project.leader,
        counselor: this.project.counselor
      }
      console.log(params);
      axios.post('/api/jsp/UpdateMidPro',qs.stringify(params))
      .then(response=>{
        if(response.data.state=='200'){
          this.$router.push(`/admin/ProjectMidManage`);
        }
      })
      .catch(err=>{
        console.log(err);
      })
    },
    // 初始化项目信息
    initMidProView(){
        let params = {
            proId:this.$route.params.proId
        }
        axios.post('/api/jsp/FindSpeMidPro',qs.stringify(params))
        .then(response=>{
            let proInfo = response.data.datas;   
            console.log(proInfo)
            this.project.name = proInfo.proObj.pro_name;
            this.project.submitStatus = proInfo.proObj.is_submit;
            this.project.submitTime = proInfo.proObj.submit_time;
            this.project.leader = proInfo.stuObj.user_name;
            this.project.counselor = proInfo.teaObj.user_name;
            // this.project.reviewer = proInfo.prorevObj.review_user_name;
            // this.project.result = proInfo.prorevObj.review_code;
            this.project.protime=proInfo.proObj.pro_status;
            this.review.userId = proInfo.prorevObj.review_user_id;
            this.review.name = proInfo.prorevObj.review_user_name;
            this.review.reviewScode = proInfo.prorevObj.review_code;
          }).catch(err=>{
              console.log(err);
          });
    },
    // 初始化文件列表
    initFileList(){
      let params = {
         id: this.proId,
         type:'中期',
      }
      axios.post('/api/jsp/ProjectMangeFileQueryByType',qs.stringify(params))
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
    // 上传文件成功之后调用函数
    handleSuccess(res,file,fileList){
      // this.fromData.id = res.datas;
      console.log(res)
    },
    // 上传文件
    submitUpload() {
      this.isUpload = true;
      this.$refs.upload.submit();
    },
    // 删除一个文件
    handleRemove(file, fileList) {
      let params = {
        id:this.proId,
        type: "中期",
      }
      axios.post('/api/jsp/ProjectMangeFileDeleteByType',qs.stringify(params))
      .then(response=>{
        console.log(response)
      })
    },
    // 查看一个文件信息
    handlePreview(file) {
      console.log(file);
    },
    toDelete(){},
    // 下载所有文件
    downloadAll(){  
       window.open("http://localhost:8080/jsp/ProjectMangeFileDownload?type=中期&id="+this.proId);
    },
    // 下载单个文件
    downloadOne(){},
    // 删除全部文件
    deleteAllFile(){
      let params = {
        id: this.proId,
        type:'中期',
      }
      axios.post('/api/jsp/ProjectMangeFileDeleteByType',qs.stringify(params))
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
.message-members{
  display: none;
  margin: 30px 0 0 0;
}
.message-wrapper-item{
  margin-top: 40px;
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
.btn-check{
  margin-left: 12px;
}
.itemshow{
  margin-top: 10px;
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
.check{
  display: none;
}
</style>
