<template>
  <div class="container">
    <h2 class="title">项目报名审核</h2>
    <div class="head-info-wrapper">
      <div>
        <span>项目管理 > </span>
        <span>项目报名审核</span>
      </div>
      <div class="btn-wrap">
        <el-button type="primary" size="small" @click="reProAuditStateSuccess">审核成功</el-button>
        <el-button type="danger" size="small" @click="reProAuditStateFail">审核失败</el-button>
        <el-button type="primary" size="small" @click="exit">返回</el-button>

      </div>
    </div>
    <div class="info-wrapper">
      <div class="message-project">
        <div class="message-title">
          <span>项目基本信息</span>
        </div>
        <div class="message-wrapper">
          <el-row>
            <el-col :span="6">
              <span class="item-name">项目名称</span>
              <div class="item-content">
                <el-select v-model="project.name" disabled></el-select>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">项目负责人</span>
              <div class="item-content">
                <el-input v-model="project.leader" disabled></el-input>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">负责人手机号</span>
              <div class="item-content">
                <el-input v-model="project.leaderPhone" disabled></el-input>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">指导老师</span>
              <div class="item-content">
                <el-input v-model="project.counselor" disabled></el-input>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <span class="item-name">指导老师手机号</span>
              <div class="item-content">
                <el-input v-model="project.counselorPhone" disabled></el-input>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">开始时间</span>
              <div class="item-content">
                <el-date-picker  disabled
                  v-model="project.startTime"
                  type="date"
                  :picker-options="pickerOptions1">
                </el-date-picker>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
      <div class="message-members">
        <div class="message-title">
          <span>报名人信息</span>
        </div>
        <div class="message-wrapper">
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
                <el-input disabled v-model="user.phone"></el-input>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <span class="item-name">报名时间</span>
              <div class="item-content">
                <el-date-picker  disabled
                  v-model="user.recruitTime"
                  type="date"
                  :picker-options="pickerOptions1">
                </el-date-picker>
                </div>
            </el-col>
            <el-col :span="6">
                <span class="item-name">审核状态</span>
                <div class="item-content">
                  <el-input disabled v-model="user.reviewStatus"></el-input>
                </div>
            </el-col>
          </el-row>
        </div>
      </div>
      <div class="message-file">
        <div class="message-title">
          <span>附件</span>
        </div>
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
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios'
import qs from 'qs'
export default {
  data () {
    return {
      pickerOptions1: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
      projectName: [{
        name: '学音乐'
      }, {
        name: 'yayaya'
      }, {
        name: '饿了吗'
      }],
      project: {
        proId: '',
        name: "",
        status: '',
        leader: '',
        leaderPhone: '',
        counselor:'',
        counselorPhone: '',
        startTime: '',
      },
      user: {
        userId: '',
        name: "",
        email: "",
        phone: '',
        recruitTime: '',
        reviewStatus: ''
      },
      recId: '',
      fileList: [],
    }
  },
  components: {

  },
  mounted:function(){
    // 获取路由上的type参数
    // this.type = this.$route.params.type;
    this.recId = this.$route.params.recId;
    this.initProAudit();
  },
  methods: {
    initProAudit(){
      let params = {
        recId:this.recId
      }
      console.log(params);
      axios.post('/api/jsp/FindProAuditById',qs.stringify(params))
      .then(response=>{
        
        let proObj = response.data.datas.proObj;
        let teaObj = response.data.datas.teaObj;
        let leaderObj = response.data.datas.leaderObj;
        let signUpObj = response.data.datas.signUpObj;
        // 项目信息
        this.project.proId = proObj.pro_id;
        this.project.name = proObj.pro_name;
        this.project.status = proObj.pro_status;
        this.project.startTime = proObj.start_year;

        this.project.leader = leaderObj.user_name;
        this.project.leaderPhone = leaderObj.user_phone;

        this.project.counselor = teaObj.user_name;
        this.project.counselorPhone = teaObj.user_phone;

        this.user.userId = signUpObj.user_id;
        this.user.name = signUpObj.user_name;
        this.user.email = signUpObj.email;
        this.user.phone = signUpObj.phone_number;
        this.user.recruitTime = signUpObj.sign_up_time;
        this.user.reviewStatus = signUpObj.state;
      })
      .catch(err=>{
        console.log(err);
      })
    },
    reProAuditStateSuccess(){
       var mystate="审核成功";
        let params = {
          recId: this.recId,
          state: mystate,
          proId:this.project.proId,
          userId: this.user.userId,
        }
        console.log(params);
        axios.post('/api/jsp/ReProAuditState',qs.stringify(params))
        .then(response=>{
          console.log(response.data.msg);
          if(response.data.state=='200'){
            this.user.reviewStatus = "审核成功"
            console.log("更改成功");
          }
        }).catch(err=>{
            console.log(err);
        });
    },
    reProAuditStateFail(){
       var mystate="审核失败";
        let params = {
          recId: this.recId,
          state: mystate,
          proId:this.project.proId,
          userId: this.user.userId,
        }
        console.log(params);
        axios.post('/api/jsp/ReProAuditState',qs.stringify(params))
        .then(response=>{
          console.log(response.data.msg);
          if(response.data.state=='200'){
            this.user.reviewStatus = "审核失败"
            console.log("更改成功");
          }
        }).catch(err=>{
            console.log(err);
        });
    },
    exit(){
      this.$router.push(`/admin/ProjectAudit`);
    },
    submitUpload() {
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    // 下载所有文件
    downloadAll(){  
      //  window.open("http://localhost:8080/jsp/NotifyMangeDownload?id="+this.notId);
    },
    // 下载单个文件
    downloadOne(){},
    // 删除全部文件
    deleteAllFile(){
      // let params = {
      //   id:this.notId
      // }
      // axios.post('/api/jsp/NotifyMangeFilesDeleteAll',qs.stringify(params))
      // .then(response=>{
      //   console.log(response)
      //   if(response.data.state==200){
      //     this.fileList = [];
      //   }
      // })
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
  padding: 10px 3%;
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
.message-members,
.message-file{
  margin: 30px 0 0 0;
}
.message-title{
  margin-bottom: 15px;
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
</style>
