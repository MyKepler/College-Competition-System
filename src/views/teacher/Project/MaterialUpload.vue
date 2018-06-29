<template>
  <div class="container">
    <h2 class="title">{{project.name}}</h2>
    <div class="head-info-wrapper">
      <div>
        <span>材料管理 > 材料上传</span>
      </div>
    </div>
    <div class="info-wrapper">

      <div class="message-file">
        <div class="message-title">
          <el-row>
            <el-col :span="2">
                <span>材料阶段</span>
            </el-col>
            <el-col :span="6">
              <el-select v-model="project.stage">
                  <el-option
                    v-for="item in options2"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
              </el-select>
            </el-col>
            <el-col :span="12">
              <el-upload
                  class="upload-demo"
                  ref="upload"
                  action="/api/jsp/ProjectSignUpFileUpload"
                  :on-success="handleSuccess"
                  :data="fromData1"
                  :file-list="fileList"
                  :auto-upload="false">
                  <el-button slot="trigger" size="small" type="primary">选取附件</el-button>
                  <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
                  <el-button style="margin-left: 10px;"   size="small" type="danger" @click="deleteAllFile">删除全部</el-button>
                </el-upload>
            </el-col>
          </el-row> 
        </div>
      </div>
      <div class="message-file">
        <div class="message-title">
          <span>申请材料</span>
          <el-button style="margin-left: 10px;"   size="small" type="danger" @click="deleteAllFileSign">删除申请材料</el-button>
        </div>
        <el-row>
          <el-col :span="16">
            <div class="item-content" >
               <el-upload
                class="upload-demo" 
                action=""
                :file-list="fileList1"
                :on-remove="handleRemoveSign"
                :auto-upload="false">
                <!-- <el-button slot="trigger" size="small" type="primary">选取附件</el-button> -->
                <!-- <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUploadMid">上传到服务器</el-button> -->
              </el-upload>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="message-file">
        <div class="message-title">
          <span>中期材料</span>
          <el-button style="margin-left: 10px;"   size="small" type="danger" @click="deleteAllFileMid">删除中期材料</el-button>
        </div>
        <el-row>
          <el-col :span="16">
            <div class="item-content" >
               <el-upload
                class="upload-demo"
                action=""
                :on-remove="handleRemoveMid"
                :file-list="fileList2"
                :auto-upload="false">
                <!-- <el-button slot="trigger" size="small" type="primary">选取附件</el-button> -->
                <!-- <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUploadMid">上传到服务器</el-button> -->
              </el-upload>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="message-file">
        <div class="message-title">
          <span>结题材料</span>
          <el-button style="margin-left: 10px;"   size="small" type="danger" @click="deleteAllFileEnd">删除结题材料</el-button>
        </div>
        <el-row>
          <el-col :span="16">
            <div class="item-content">
              <el-upload
                class="upload-demo"
                action=""
                :on-remove="handleRemoveEnd"
                :file-list="fileList3"
                :auto-upload="false">
                <!-- <el-button slot="trigger" size="small" type="primary">选取附件</el-button> -->
                <!-- <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUploadEnd">上传到服务器</el-button> -->
              </el-upload>
            </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import { quillEditor } from 'vue-quill-editor'
import axios from 'axios'
import qs from 'qs'
import util,{ getDate } from './../../../util/util.js'
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
      project: {
        name: "",
        id:"",
        stage:"申请",
      },
      fileList:[],
      fileList1: [  
        // {name: '', url: ''}
      ],
      fileList2: [  
        // {name: '', url: ''}
      ],
      fileList3: [  
        // {name: '', url: ''}
      ],
    }
  },
  computed: {
    fromData1:function(){
      return {
        id: this.project.id,
        type: this.project.stage,
        state: '可用',
        time: getDate(),
      }
    },
    // fromData2:function(){
    //   return {
    //     id: this.project.id,
    //     type: '中期',
    //     state: '可用',
    //     time: getDate(),
    //   }
    // },
    // fromData3:function(){
    //   return {
    //     id: this.project.id,
    //     type: '结题',
    //     state: '可用',
    //     time: getDate(),
    //   }
    // },
  },
  mounted:function(){

    this.project.name = this.$route.params.proName;
    this.project.id = this.$route.params.proId;
    this.initFileListSign();
    this.initFileListMid();
    this.initFileListEnd();
  },
  methods: {
    // 申请
    // 上传文件成功之后调用函数
    handleSuccess(res,file,fileList){
      // this.fromData.id = res.datas;
      console.log(res);
      if(res.state == 200){
        this.fileList=[];
        this.fileList1=[];
        this.fileList2=[];
        this.fileList3=[];
        this.initFileListSign();
        this.initFileListMid();
        this.initFileListEnd();
      }
    },
    // 上传文件
    submitUpload() {
      // this.isUpload = true;
      console.log(this.project.stage)
      this.$refs.upload.submit();
    },
    deleteAllFile(){
      let params = {
        id: this.project.id,
      }
      axios.post('/api/jsp/ProjectMangeFileDeleteAll',qs.stringify(params))
      .then(response=>{
        console.log(response)
        if(response.data.state==200){
          this.fileList1 = [];
          this.fileList2 = [];
          this.fileList3 = [];
        }
      })
    },



    // 删除一个文件
    handleRemoveSign(file, fileList) {
      let params = {
        id:this.project.id,
        type: "申请",
      }
      axios.post('/api/jsp/ProjectMangeFileDeleteByType',qs.stringify(params))
      .then(response=>{
        console.log(response)
      })
    },
    // 删除全部文件
    deleteAllFileSign(){
      let params = {
        id: this.project.id,
        type:'申请',
      }
      console.log(params);
      axios.post('/api/jsp/ProjectMangeFileDeleteByType',qs.stringify(params))
      .then(response=>{
        console.log(response)
        if(response.data.state == 200){
          this.fileList1 = [];
        }
      })
    },
    initFileListSign(){
      let params = {
         id: this.project.id,
         type:'申请',
      }
      axios.post('/api/jsp/ProjectMangeFileQueryByType',qs.stringify(params))
      .then(response=>{
        let fileData = response.data.datas;
        console.log(response);
        fileData.forEach(element => {
          this.fileList1.push({
            name: element.fileName,
            url: element.filePath,
          })
        });
        console.log(this.fileList1)
      })
      .catch(err=>{
        console.log(err);
      })
    },

  
     // 删除一个文件
    handleRemoveMid(file, fileList) {
      let params = {
        id:this.project.id,
        type: "中期",
      }
      axios.post('/api/jsp/ProjectMangeFileDeleteByType',qs.stringify(params))
      .then(response=>{
        console.log(response)
      })
    },
    // 删除全部文件
    deleteAllFileMid(){
      let params = {
        id: this.project.id,
        type:'中期',
      }
      axios.post('/api/jsp/ProjectMangeFileDeleteByType',qs.stringify(params))
      .then(response=>{
        console.log(response)
        if(response.data.state==200){
          this.fileList2 = [];
        }
      })
    },
    initFileListMid(){
      let params = {
         id: this.project.id,
         type:'中期',
      }
      axios.post('/api/jsp/ProjectMangeFileQueryByType',qs.stringify(params))
      .then(response=>{
        let fileData = response.data.datas;
        fileData.forEach(element => {
          this.fileList2.push({
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

     // 删除一个文件
    handleRemoveEnd(file, fileList) {
      let params = {
        id:this.project.id,
        type: "结题",
      }
      axios.post('/api/jsp/ProjectMangeFileDeleteByType',qs.stringify(params))
      .then(response=>{
        console.log(response)
      })
    },
    // 删除全部文件
    deleteAllFileEnd(){
      let params = {
        id: this.project.id,
        type:'结题',
      }
      axios.post('/api/jsp/ProjectMangeFileDeleteByType',qs.stringify(params))
      .then(response=>{
        console.log(response)
        if(response.data.state==200){
          this.fileList3 = [];
        }
      })
    },
    initFileListEnd(){
      let params = {
         id: this.project.id,
         type:'结题',
      }
      axios.post('/api/jsp/ProjectMangeFileQueryByType',qs.stringify(params))
      .then(response=>{
        let fileData = response.data.datas;
        fileData.forEach(element => {
          this.fileList3.push({
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
  padding: 0 3%;
  background-color: #ffffff;
}
.el-row:last-child{
  margin-bottom: 0;
}
.el-col {
  border-radius: 4px;
}
.message-file{
  margin: 0 0 80px 0;
}
.message-title{
  margin: 15px 0;
  padding: 10px 0;
  font-size: 16px;
  border-bottom: 1px solid gray;
}
.item-content{
  margin-top: 15px;
}

</style>
