<template>
<el-row>
  <div class="container loginAndRegister">
    <div class="decorate">
      <div class="project_title"><i class="el-icon-edit" style="font-size: 40px;"></i> 项目标题</div>
    </div>

    <div class="box">
      <el-tabs v-model="activeName" @tab-click="handleClick" class="tabs">
        <el-tab-pane label="登录" name="Login" class="tab_item">
          <el-form :model="loginForm">
            <el-input
              placeholder="请输入工(学)号"
              prefix-icon="el-icon-view"
              v-model="loginForm.id"
              style="margin-top: 20px;margin-bottom: 15px;">
            </el-input>
            <el-input
              type="password"
              placeholder="请输入密码"
              prefix-icon="el-icon-news"
              v-model="loginForm.password"
              style="margin-bottom: 15px;">
            </el-input>
          </el-form>

          <el-button type="primary" style="width: 100%; font-size: 15px; margin-bottom: 15px;"
                     @click="handleLogin">登录</el-button>

          <div class="tail">
            <el-switch
              v-model="loginForm.isRememberPassword"
              active-color="#13ce66"
              inactive-color="#cccccc">
            </el-switch>

            <span style="font-size: 13px; color: #aaaaaa">一周内免输密码</span>
            <span style="color: #aaaaaa">|</span>

            <router-link to="/" class="forgetPassword">忘记密码</router-link>
          </div>
        </el-tab-pane>

        <el-tab-pane label="注册" name="Register" class="tab_item">
          <el-form label-position="left" label-width="70px" :model="registerForm">
            <el-form-item label="学号">
              <el-input v-model="registerForm.studentId"></el-input>
            </el-form-item>
            <el-form-item label="姓名">
              <el-input v-model="registerForm.name"></el-input>
            </el-form-item>
            <el-form-item label="性别">
              <el-switch v-model="registerForm.gender"
                         active-text="女"
                         inactive-text="男"
                         active-color="pink"
                         inactive-color="green"
              style="float:left;margin-top:7px;"></el-switch>
            </el-form-item>
            <el-form-item label="手机号">
              <el-input v-model="registerForm.tele"></el-input>
            </el-form-item>
            <el-form-item label="验证码">
              <el-input v-model="registerForm.verifyCode"></el-input>
            </el-form-item>
            <el-form-item label="邮箱">
              <el-input v-model="registerForm.email"></el-input>
            </el-form-item>
            <el-form-item label="学院">
              <el-select v-model="registerForm.institute" placeholder="请选择" class="chice" @change="initMajor(registerForm.institute)">
                <el-option
                  v-for="item in instituteOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="专业">
              <el-select v-model="registerForm.major" placeholder="请选择" class="chice" :disabled="isAcademy" @change="initClass(registerForm.major)">
                <el-option
                  v-for="item in majorOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="班级">
              <el-select v-model="registerForm.clazz" placeholder="请选择" class="chice" :disabled="isMajor">
                <el-option
                  v-for="item in clazzOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="设置密码">
              <el-input v-model="registerForm.password"></el-input>
            </el-form-item>
            <el-form-item label="确认密码">
              <el-input v-model="registerForm.re_password"></el-input>
            </el-form-item>
          </el-form>
          <el-button type="primary" style="width: 100%; font-size: 15px; margin-bottom: 15px;"
                     @click="handleRegister">注册</el-button>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</el-row>
</template>
<script>
//import router from "../router/index";
import axios from 'axios'
import qs from 'qs'
import util from './../util/util.js'
import { setCookie,delCookie,getCookie } from './../util/util.js'
export default {
  data() {
    return {
      isAcademy: true,
      isMajor: true,
      loginForm: {
        //登录信息
        id: "",
        password: "",
        isRememberPassword: true,
      },
      registerForm: {
        //注册信息
        studentId: "",
        name: "",
        gender: "",
        tele: "",
        //验证码固定
        verifyCode: "",
        email: "",
        institute: "",
        major: "",
        clazz: "",
        password: "",
        re_password: ""
      },
      instituteOptions: [],
      majorOptions: [],
      clazzOptions: []
    };
  },
  mounted: function() {
    this.initAcademy();
    if(getCookie("userId")){
      this.loginForm.id = getCookie("userId");
    }
  },
  computed:{
    activeName:{
      get:function(){
        return this.$route.name;
      },
      set:function(){

      }
    },
  },
  methods: {
    // 学院数据
    initAcademy(){
      axios.get('/api/jsp/FindAllAca')
      .then(response=>{
        response.data.datas.forEach(element => {
          this.instituteOptions.push({
            value:element.academy,
            label:element.academy,
          })
        });
        //console.log(this.instituteOptions);
      }).catch(err=>{
        console.log(err);
      });
    },
    // 选中学院之后在加载专业数据
    initMajor(institute){
      this.isAcademy = false;
      let params = {
        academy: institute
      }
      axios.post('/api/jsp/FindAllMajor',qs.stringify(params))
      .then(response=>{
        response.data.datas.forEach(element=>{
          this.majorOptions.push({
            value:element.major,
            label:element.major,
          })
        });
        // console.log(this.majorOptions);
      }).catch(err=>{
        console.log(err);
      });
    },
    // 选中专业之后加载班级数据
    initClass(major){
      this.isMajor= false;
      let params = {
        major: major
      }
      axios.post('/api/jsp/FindAllClass',qs.stringify(params))
      .then(response=>{
        response.data.datas.forEach(element=>{
          this.clazzOptions.push({
            value:element._class,
            label:element._class,
          })
        });
        // console.log(this.clazzOptions);
      }).catch(err=>{
        console.log(err);
      });
    },
    handleClick(tab, event) {
      console.log(tab,event);
    },
    // 登陆 并且实现记住用户
    handleLogin() {
      axios.post('/api/jsp/Login',qs.stringify(this.loginForm))
      .then(response=>{
        let res = response.data;
        let exdays = 24 * 60 * 60 * 1000;
        if(res.state == "200"){
          this.$store.commit('login', {user: res.datas[0]});
          delCookie("userId");
          // delCookie("userName");
          // delCookie("userIdentity");
          // 存入cookie
          setCookie("userId",res.datas[0].user_id,exdays);
          // setCookie("userName",res.datas[0].user_name,exdays);
          // setCookie("userIdentity",res.datas[0].user_identity,exdays);
          //记住密码
          console.log(this.loginForm.isRememberPassword)
          if(this.loginForm.isRememberPassword){
            // window.localStorage.setItem('token', res.data.token)
            window.localStorage.setItem('user', JSON.stringify(res.datas[0]));
            window.localStorage.setItem('userId', this.loginForm.password);
          }
          this.$router.push('/');
        }
      }).catch(err=>{
        console.log(err);
      });
    },
    // 只能学生注册
    handleRegister(){
      axios.post('/api/jsp/Register',qs.stringify(this.registerForm))
      .then(response=>{
        //console.log(response.data);
        let res = response.data;
        if(res.state == "200"){
          this.$store.commit('login', {user: res.datas[0]});
          //记住密码
          if(this.loginForm.isRememberPassword){
            // window.localStorage.setItem('token', res.data.token)
            window.localStorage.setItem('user', JSON.stringify(res.datas[0]));
          }
          this.$router.push('/');
        }
      })
      .catch(err=>{
        console.log(err);
      });
    },
  }
};
</script>
<style scoped>
.container {
  width: 100%;
  background-color: #f5f5f5;
  padding-top: 100px;
  padding-bottom: 100px;
  min-height: 100%;
  margin-top: -100px;
  height:800px;
}
.box {
  /* min-width: 500px; */
  width: 400px;
  margin: auto;
  position:relative;
  left:15%;
  border: 1px solid #e4e4e4;
  padding: 20px 60px;
  box-shadow: 0 0 2px gray;
  background-color: #fff;
}

.forgetPassword {
  font-size: 14px;
  color: #aaaaaa;
}
.forgetPassword:hover {
  color: #4586ff;
}
.decorate {
  position: absolute;
  left: 100px;
  top: 100px;
}
.project_title {
  color: #5394c5;
  font-size: 40px;
  font-weight: bold;
}
.chice{
  width: 100%;
}
</style>
<style>
.loginAndRegister .el-tabs__nav-wrap {
  margin: auto;
}
.loginAndRegister .el-tabs__item {
  width: 50% !important;
  text-align: center;
  font-size: 20px;
}
.loginAndRegister .el-tabs__nav {
  width: 100%;
}
</style>
