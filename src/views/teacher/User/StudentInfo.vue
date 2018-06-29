<template>
  <div class="container">
    <h2 class="title">学生信息管理</h2>
    <div class="head-info-wrapper">
      <div>
        <span>用户管理 > </span>
        <span>学生信息查看</span>
      </div>
    </div>
    <div class="info-wrapper">
      <div class="info-base">
        <div class="message-title">
          <span>用户基本信息</span>
        </div>
        <el-row>
          <el-col :span="8">
              <span class="item-name" >用户ID</span>
              <div class="item-content">
                <el-input disabled v-model="user.userId" disabled></el-input>
              </div>
          </el-col>
          <el-col :span="8">
              <span class="item-name" >姓名</span>
              <div class="item-content">
                <el-input v-model="user.name" disabled></el-input>
              </div>
          </el-col>
          <el-col :span="8">
              <span class="item-name">性别</span>
              <div class="item-content">
                <el-select v-model="user.sex" disabled>
                </el-select>
              </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <span class="item-name">邮箱</span>
            <div class="item-content">
              <el-input v-model="user.email" disabled></el-input>
            </div>
          </el-col>
          <el-col :span="8">
            <span class="item-name">手机号</span>
            <div class="item-content">
              <el-input v-model="user.phone" disabled></el-input>
            </div>
          </el-col>
          <el-col :span="8">
            <span class="item-name">用户状态</span>
            <div class="item-content">
              <el-select v-model="user.status" disabled>
              </el-select>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <span class="item-name">用户类别</span>
            <div class="item-content">
              <el-select v-model="user.userType" disabled>
              </el-select>
            </div>
          </el-col>
          <el-col :span="8">
            <span class="item-name">密码</span>
            <div class="item-content">
              <el-input type="password" v-model="user.password" disabled></el-input>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="info-student">
        <div class="message-title">
          <span>学生附加信息</span>
        </div>
        <el-row>
          <el-col :span="8">
              <span class="item-name">学院</span>
              <div class="item-content">
                <el-select v-model="student.academy" disabled>
                </el-select>
              </div>
          </el-col>
          <el-col :span="8">
              <span class="item-name">专业</span>
              <div class="item-content">
                <el-select v-model="student.major" disabled>
                </el-select>
              </div>
          </el-col>
          <el-col :span="8">
              <span class="item-name" >班级</span>
              <div class="item-content">
                <el-select v-model="student.classes" disabled>
                </el-select>
              </div>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
// require styles
import axios from 'axios'
import qs from 'qs'
export default {
  data () {
    return {
      user: {
        userId: '',
        name: "",
        sex: '',
        email: '',
        phone: '',
        status: '',
        userType: '',
        password:'',
      },
      student: {
        academy: '',
        major: '',
        classes: ''
      },
    }
  },
  mounted:function(){
    this.userId = this.$route.params.userId;
    this.initStuPersonal();
    
  },
  components: {

  },
  methods: {
    initStuPersonal(){
      let params = {
        userId:this.userId
      }
      axios.post('/api/jsp/FindSpeStu',qs.stringify(params))
      .then(response=>{
        //console.log(response.data.datas.user_id);
        let studentInfo = response.data.datas;
        this.user.userId = studentInfo.user_id;
        this.user.name = studentInfo.user_name;
        this.user.sex = studentInfo.user_sex;
        this.user.email = studentInfo.user_mail;
        this.user.phone = studentInfo.user_phone;
        this.user.status = studentInfo.account_state;
        this.user.userType = studentInfo.user_identity;
        this.user.password = studentInfo.user_pwd;

        this.student.academy = studentInfo.student_academy;
        this.student.major = studentInfo.student_major;
        this.student.classes = studentInfo.student_class;
        // console.log(this.user);
        // console.log(this.student);
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
.btn-confirm{
  padding: 8px 18px;
}
.info-wrapper{
  margin: auto;
  padding: 0 3%;
  background-color: #ffffff;
}
.info-student{
  margin-top: 30px;
}
.message-title{
  margin: 0 0 15px 0;
  padding: 10px 0;
  font-size: 16px;
  border-bottom: 1px solid gray;
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
.item-name{
  font-size: 16px;
}
.item-content{
  margin-top: 15px;
}
.el-input,.el-select{
  width:85%;
}
</style>
