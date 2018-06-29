<template>
  <div class="container">
    <h2 class="title">学生报名管理</h2>
    <div class="head-info-wrapper">
      <div>
        <span>项目管理 > </span>
        <span>学生报名管理</span>
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
                <el-select placeholder="请输入项目名称" v-model="project.name" disabled>
                </el-select>
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
                <el-input placeholder="请输入你的学号" v-model="user.userId" disabled></el-input>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">姓名</span>
              <div class="item-content">
                <el-input placeholder="请输入你的姓名" v-model="user.name" disabled></el-input>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">邮箱</span>
              <div class="item-content">
                <el-input placeholder="请输入你的邮箱" v-model="user.email" disabled></el-input>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">手机号</span>
              <div class="item-content">
                <el-input placeholder="请输入你的手机号" v-model="user.phone" disabled></el-input>
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
      project: {
        name: "学音乐",
        leader: '林海瑞',
        leaderPhone: '15356798520',
        counselor:'石兴民',
        counselorPhone: '15356798520',
        startTime: '1990-01-01',
      },
      user: {
        userId: '',
        name: '',
        email: '',
        phone: '',
        recruitTime: '1900-01-01',
        reviewStatus: '未审核'
      },
      fileList: [{name: 'food.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}, {name: 'food2.jpeg', url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100'}],
      userId:"",
      proId:"",
    }
  },
  components: {

  },
  mounted:function(){
    // 获取路由上的type参数
    this.proId = this.$route.params.proId;
    this.userId = this.$route.params.userId;
    this.initData();
  },
  methods: {

    initData(){
      let params = {
        id: this.proId
      }
      console.log(params);
      axios.post('/api/jsp/StudentSignUpSelectOneEnableProjects',qs.stringify(params))
      .then(response=>{
        console.log(response);
        let pro = response.data.datas;
        this.project.name = pro.projectName;
        this.project.leader = pro.projectPrincipalName;
        this.project.leaderPhone = pro.projectPrincipalPhoneNumber;
        this.project.counselor = pro.projectTeacherName;
        this.project.counselorPhone = pro.projectTeacherPhoneNumber;
        this.project.startTime = pro.startYear;
      })
      .catch(err=>{
        console.log(err);
      })
      let params2 = {
        projectId: this.proId,
        userId: this.userId,
      }
      console.log(params2);
      axios.post('/api/jsp/StudentSignUpEachDetails',qs.stringify(params2))
      .then(response=>{ 
        console.log(response);
        let user = response.data.datas;
        this.user.userId = user.userId;
        this.user.name  = user.userName;
        this.user.email = user.email;
        this.user.phone = user.phoneNumber;
        this.user.recruitTime = user.signUpTime;
        this.user.reviewStatus  = user.state;
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
