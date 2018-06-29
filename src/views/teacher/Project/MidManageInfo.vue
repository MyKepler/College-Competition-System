<template>
  <div class="container">
    <h2 class="title">项目中期管理</h2>
    <div class="head-info-wrapper">
      <div>
        <span>项目管理 > </span>
        <span>项目中期查看管理</span>
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
            <span class="item-name">提交状态</span>
            <div class="item-content">
                <el-select v-model="project.submitStatus" disabled>
                </el-select>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">材料提交时间</span>
            <div class="item-content">
              <el-date-picker
                disabled
                v-model="project.submitTime"
                type="date"
                :picker-options="pickerOptions1">
              </el-date-picker>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">负责人</span>
            <div class="item-content">
              <el-input placeholder="请输入负责人" v-model="project.leader" disabled></el-input>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <span class="item-name">指导老师</span>
            <div class="item-content">
              <el-input placeholder="请输入指导老师" v-model="project.counselor" disabled></el-input>
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
      <div class="message-file">
        <div class="message-title">
          <span>附件</span>
        </div>
        <el-row>
          <el-col :span="16">
            <div class="item-content">
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
            <div class="item-content" v-if="fileList.length == 0">
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
          return time.getTime() > Date.now();
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
      fileList: [],
      isUpload: false,
      proId: '',
    }
  },
  components: {

  },
  mounted:function(){
    // 获取路由上的参数
    this.proId = this.$route.params.proId;
    this.initMidProView();
    this.initFileList();
  },
  methods: {
    initMidProView(){
        let params = {
            proId:this.$route.params.proId
        }
        console.log(params);
        axios.post('/api/jsp/FindSpeMidPro',qs.stringify(params))
        .then(response=>{
            let proInfo = response.data.datas;

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
        console.log(response)
      })
      .catch(err=>{
        console.log(err);
      })
    },  
     // 下载所有文件
    downloadAll(){
      //  window.open("http://localhost:8080/jsp/NotifyMangeDownload?id="+this.notId);
    },
    // 下载单个文件
    downloadOne(){},
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
