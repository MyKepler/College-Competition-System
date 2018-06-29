<template>
  <div class="container">
    <h2 class="title">通知管理</h2>
    <div class="head-info-wrapper">
      <div>
        <span v-if= "type==='0'">通知查看管理</span>
        <span v-if= "type==='1'">通知修改管理</span>
      </div>
      <div class="btn-wrap" v-if= "type==='0'">
        <el-button type="primary" size="small" @click="toUpdate">修改通知</el-button>
      </div>
      <div class="btn-wrap" v-if= "type==='1'">
        <el-button type="primary" plain size="small" @click="cancel">取消修改</el-button>
        <el-button type="warning" plain size="small" @click="affirm">确认修改</el-button>
      </div>
    </div>
    <div class="info-wrapper">
      <!--输入域-->
      <el-row>
        <el-col :span="8">
          <span class="item-name">标题</span>
          <div class="item-content">
            <el-input placeholder="请输入标题" v-model="notify.title" :disabled="isCheck"></el-input>
          </div>
        </el-col>
        <el-col :span="8">
          <span class="item-name">状态</span>
          <div class="item-content">
            <el-select v-model="notify.status" disabled>
              <el-option
                v-for="item in options2"
                :key="item.value"
                :label="item.label"
                :value="item.value">
              </el-option>
            </el-select>
          </div>
        </el-col>
        <el-col :span="8">
          <span class="item-name">发布时间</span>
          <div class="item-content">
            <el-date-picker
              :disabled="isCheck"
              v-model="notify.time"
              type="date"
              value-format="yyyy-MM-dd"
              :picker-options="pickerOptions1">
            </el-date-picker>
          </div>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <span class="item-name">发布者</span>
          <div class="item-content">
            <el-input placeholder="请输入发布者" v-model="notify.publishName" :disabled="isCheck"></el-input>
          </div>
        </el-col>
      </el-row>
      <!-- 文本编译器 -->
      <el-row>
        <el-col :span="24">
          <span class="item-name">发布内容介绍</span>
          <div class="item-content">
            <quill-editor :disabled="isCheck" v-model="notify.introduce"></quill-editor>
          </div>
        </el-col>
      </el-row>
      <!-- 附件 -->
      <el-row>
        <el-col :span="16">
          <span class="item-name">附件</span>
          <div class="item-content" v-if= "type==='1'">
            <el-upload
              class="upload-demo"
              ref="upload"
              action="/api/jsp/NotifyMangeUpload"
              :on-preview="handlePreview"
              :on-remove="handleRemove"
              :file-list="fileList"
              :data="fromData"
              :auto-upload="false">
              <el-button slot="trigger" size="small" type="primary">选取附件</el-button>
              <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
              <el-button style="margin-left: 10px;" size="small" type="danger" @click="deleteAllFile">删除全部</el-button>
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
</template>

<script>
// require styles
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import { quillEditor } from 'vue-quill-editor'
import axios from 'axios'
import qs from 'qs'
export default {
  data () {
    return {
      options2: [{
        value: '发布',
        label: '发布'
      }, {
        value: '未发布',
        label: '未发布',
      }, {
        value: '禁用',
        label: '禁用',
      }],
      pickerOptions1: {
        disabledDate(time) {
          // return time.getTime() > Date.now();
        }
      },
      notify: {
        id:'',
        title: "",
        status: '',
        time: '',
        publishName:'',
        introduce: '',
      },
      fileList:[],
      type: '0',   //0为查看通知；1为修改通知
      isCheck: true,
      notId: '',
    }
  },
  computed:{
    fromData:function(){
      return {id: this.notId};
    }
  },
  mounted:function(){
    // 获取路由上的type参数
    this.type = this.$route.params.type;
    this.notId = this.$route.params.notId;
    this.isCheck = this.type == '0'?true:false
    this.initSpeNotify();
    // this.fromData.id = this.notId;
  },
  components: {
    quillEditor,
  },
  methods: {
    // 上传按钮成功事件
    handleSuccess(res,file,fileList){
      this.fromData.id = res.datas;
      console.log("上传成功啦"+this.fromData.id);
    },
    // 点击上传到服务器
    submitUpload() {
      // console.log(this.fromData)
      this.$refs.upload.submit();
    },
    // 删除一个文件
    handleRemove(file, fileList) {
      let params = {
        id:this.notId,
        path: file.url,
      }
      axios.post('/api/jsp/NotifyMangeFilesDeleteOne',qs.stringify(params))
      .then(response=>{
        console.log(response)
        if(response.data.state == 200){
          console.log("成功删除一个文件");
        }
      })
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    // 跳转到修改界面
    toUpdate(){
      this.type = '1';
      if(this.type == '0'){
        this.isCheck = true;
      }else{
        this.isCheck = false;
      }
      this.$router.push(`/Notifyinfo/1/${this.notId}`);
    },
    // 取消修改
    cancel(){
      this.initSpeNotify();
    },
    // 确认修改
    affirm(){
      let params ={
        id: this.notId,
        title: this.notify.title,
        time: this.notify.time,
        name: this.notify.publishName,
        introduction: this.notify.introduce,
      }
      // console.log(params)
      axios.post('/api/jsp/NotifyMangeEdit',qs.stringify(params))
      .then(response=>{
        if(response.data.state=='200'){
          this.$router.push(`/admin/Notification`);
        }
      })
      .catch(err=>{
        console.log(err);
      })
    },
    // 加载通知信息（以及文件）
    initSpeNotify(){
      let params = {
        id:this.notId
      }
      axios.post('/api/jsp/NotifyManageSpeInfo',qs.stringify(params))
      .then(response=>{
        let notify = response.data.datas;
        this.notify.id = notify.id;
        this.notify.title = notify.title;
        this.notify.status = notify.state;
        this.notify.time = notify.time;
        this.notify.publishName = notify.userName;
        this.notify.introduce = notify.introduction; 
        notify.notifyFilesObj.forEach(element => {
          this.fileList.push({
            name: element.fileName,
            url: element.filePath,
          })
        });
        console.log("通知文件"+notify.notifyFilesObj)
      })
      .catch(err=>{
        console.log(err);
      })
    },
    // 下载所有文件
    downloadAll(){  
       window.open("http://localhost:8080/jsp/NotifyMangeDownload?id="+this.notId);
    },
    // 下载单个文件
    downloadOne(){},
    // 删除全部文件
    deleteAllFile(){
      let params = {
        id:this.notId
      }
      axios.post('/api/jsp/NotifyMangeFilesDeleteAll',qs.stringify(params))
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
    margin-bottom: 40px;
  }
.el-row:last-child{
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
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
.el-row.info-content {
  margin: 1.5rem 0;
}
.info-item {
  display: flex;
  align-items: center;
}
.attack-link {
  width: 100%;
}
.download {
  padding-left: 20px;
  cursor: pointer;
}
</style>
