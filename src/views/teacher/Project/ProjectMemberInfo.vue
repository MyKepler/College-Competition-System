<template>
  <div class="container">
    <h2 class="title">项目成员管理</h2>
    <div class="head-info-wrapper">
      <div>
        <span>项目成员管理 > </span>
        <span>项目成员查看</span>
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
                <el-input v-model="project.teacher" disabled></el-input>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <span class="item-name">指导老师手机号</span>
              <div class="item-content">
                <el-input v-model="project.teacherPhone" disabled></el-input>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">项目组成人数</span>
              <div class="item-content">
                <el-input v-model="project.currentNum" disabled></el-input>
              </div>
            </el-col>
          </el-row>
        </div>
      </div>
      <div class="message-members">
        <div class="message-title">
          <span>项目成员信息</span>
        </div>
        <div class="message-wrapper">
          <el-row>
            <el-col :span="6">
              <span class="item-name">学号</span>
              <div class="item-content">
                <el-input v-model="user.id" disabled></el-input>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">姓名</span>
              <div class="item-content">
                <el-input v-model="user.name" disabled></el-input>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">邮箱</span>
              <div class="item-content">
                <el-input v-model="user.email" disabled></el-input>
              </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">手机号</span>
              <div class="item-content">
                <el-input v-model="user.phone" disabled></el-input>
              </div>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
                <span class="item-name">审核状态</span>
                <div class="item-content">
                  <el-input v-model="user.statusInProject" disabled></el-input>
                </div>
            </el-col>
            <el-col :span="6">
              <span class="item-name">学院</span>
              <div class="item-content">
                <el-select v-model="student.academy" disabled>
                </el-select>
              </div>
          </el-col>
          <el-col :span="6">
              <span class="item-name">专业</span>
              <div class="item-content">
                <el-select v-model="student.major" disabled>
                </el-select>
              </div>
          </el-col>
          <el-col :span="6">
              <span class="item-name" >班级</span>
              <div class="item-content">
                <el-select v-model="student.classes" disabled>
                </el-select>
              </div>
          </el-col>
          </el-row>
        </div>
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
export default {
  data () {
    return {
      pickerOptions1: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
      project: {
        name: "",
        status: '',
        leader: '',
        leaderPhone: '',
        teacher:'',
        teacherPhone: '',
        currentNum: '',
      },
      user: {
        id: '',
        name: "",
        email: "",
        phone: '',
        statusInProject: ''
      },
      student: {
        academy: '',
        major: '',
        classes: ''
      },
      stuId: '',
      proId: '',
      fileList: []
    }
  },
  components: {

  },
  mounted:function(){
    // 获取路由上的proId和userId参数
    this.stuId = this.$route.params.userId;
    this.proId = this.$route.params.proId;
    this.initProMemPersonal();
  },
  methods: {
    //初始化
    initProMemPersonal(){
      let params = {
        proId:this.proId,
        stuId:this.stuId
      }
      console.log(params);
      axios.post('/api/jsp/FindProMemberById',qs.stringify(params))
      .then(response=>{
        console.log(response.data.datas);
        // console.log(response.data.datas.stuObj);
        // console.log(response.data.datas.teaObj);
        // console.log(response.data.datas.reviewObj);
        let proObj = response.data.datas.proObj;
        let stuObj = response.data.datas.stuObj;
        let teaObj = response.data.datas.teaObj;
        let leaderObj = response.data.datas.leaderObj
        // 项目信息
        this.project.name = proObj.pro_name;
        this.project.currentNum = proObj.act_num;
        this.project.leaderPhone = leaderObj.user_phone;
        this.project.leader = leaderObj.user_name;
        this.project.teacherPhone = teaObj.user_phone;
        this.project.teacher = teaObj.user_name;

        this.user.id = stuObj.user_id;
        this.user.name = stuObj.user_name;
        this.user.phone = stuObj.user_phone;
        this.user.email = stuObj.user_mail;
        this.user.statusInProject = stuObj.account_state;

        this.student.academy = stuObj.student_academy;
        this.student.major = stuObj.student_major;
        this.student.classes = stuObj.student_class;
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
