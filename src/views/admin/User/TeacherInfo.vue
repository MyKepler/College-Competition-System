<template>
  <div class="container">
    <h2 class="title">教师信息管理</h2>
    <div class="head-info-wrapper">
      <div>
        <span>用户管理 > </span>
        <span v-if= "type==='0'">教师信息查看</span>
        <span v-if= "type==='1'">教师信息修改</span>
      </div>
      <div class="btn-wrap" v-if= "type==='1'">
        <el-button type="primary" plain size="small" @click="cancel">取消修改</el-button>
        <el-button type="warning" plain size="small" @click="affirm">确认修改</el-button>
      </div>
      <div class="btn-wrap" v-if= "type==='0'">
        <el-button type="primary" size="small" @click="toUpdate">编辑用户信息</el-button>
        <!-- <el-button type="danger" size="small" @click="toDelete">删除该用户</el-button> -->
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
              <span class="item-name" disabled>姓名</span>
              <div class="item-content">
                <el-input v-model="user.name" :disabled="isCheck"></el-input>
              </div>
          </el-col>
          <el-col :span="8">
              <span class="item-name">性别</span>
              <div class="item-content">
                <el-select v-model="user.sex" :disabled="isCheck">
                  <el-option
                    v-for="item in options2"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <span class="item-name">邮箱</span>
            <div class="item-content">
              <el-input v-model="user.email" :disabled="isCheck"></el-input>
            </div>
          </el-col>
          <el-col :span="8">
            <span class="item-name">手机号</span>
            <div class="item-content">
              <el-input v-model="user.phone" :disabled="isCheck"></el-input>
            </div>
          </el-col>
          <el-col :span="8">
            <span class="item-name">用户状态</span>
            <div class="item-content">
              <el-select v-model="user.status" :disabled="isCheck">
                <el-option
                  v-for="item in options1"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <span class="item-name">用户类别</span>
            <div class="item-content">
              <!-- <el-select v-model="user.userType" :disabled="isCheck"> -->
                <el-select v-model="user.userType" disabled>
                <!-- <el-option
                  v-for="item in options3"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option> -->
              </el-select>
            </div>
          </el-col>
          <el-col :span="8">
            <span class="item-name">密码</span>
            <div class="item-content" v-if= "type==='0'">
              <el-input type="password" v-model="user.password" :disabled="isCheck"></el-input>
            </div>
            <div class="item-content" v-if= "type==='1'">
              <el-button type="danger" size="small" @click="rePassword">重置密码</el-button>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="info-student">
        <div class="message-title">
          <span>教师附加信息</span>
        </div>
        <el-row>
          <el-col :span="8">
              <span class="item-name">学历</span>
              <div class="item-content">
                <el-select v-model="teacher.education" :disabled="isCheck">
                  <el-option
                    v-for="item in education"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </div>
          </el-col>
          <el-col :span="8">
              <span class="item-name">学位</span>
              <div class="item-content">
                <el-select v-model="teacher.degree" :disabled="isCheck">
                  <el-option
                    v-for="item in degree"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </div>
          </el-col>
          <el-col :span="8">
              <span class="item-name" >专业</span>
              <div class="item-content">
                <el-select v-model="teacher.major" :disabled="isCheck">
                  <el-option
                    v-for="item in major"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
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
      options1: [{
        value: '可用',
        label: '可用'
      }, {
        value: '不可用',
        label: '不可用',
      }],
      options2: [{
        value: '男',
        label: '男'
      }, {
        value: '女',
        label: '女',
      }],
      // options3: [{
      //   value: '教师',
      //   label: '教师'
      // }, {
      //   value: '学生',
      //   label: '学生',
      // }, {
      //   value: '评审专家',
      //   label: '评审专家',
      // }, {
      //   value: '管理员',
      //   label: '管理员',
      // }],
      user: {
        userId: '',
        name: '',
        sex: '',
        email: '',
        phone: '',
        status: '',
        userType: '',
        password:'',
      },
      teacher: {
        education: '',
        degree: '',
        major: ''
      },
      education: [],
      degree: [],
      major: [],
      type: '0',   //0为查看通知；1为修改通知
      isCheck: true,
      userId: ''
    }
  },
  mounted:function(){
    // 获取路由上的type参数
    this.type = this.$route.params.type;
    this.userId = this.$route.params.userId;
    this.initTeaPersonal();
    this.initEducation();
    this.initDegree();
    this.initMajor();
    if(this.type == '0'){
      this.isCheck = true;
    }else{
      this.isCheck = false;
    }

  },
  components: {

  },
  methods: {
     // 取消修改
    cancel(){
      this.initTeaPersonal();
    },
    // 确认修改
    affirm(){
      let params ={
        userId: this.userId,
        name: this.user.name,
        sex: this.user.sex,
        email: this.user.email,
        phone: this.user.phone,
        status: this.user.status,
        userType: this.user.userType,
        degree: this.teacher.degree,
        bachelor: this.teacher.education,
        major: this.teacher.major,
      }
      console.log(params)
      axios.post('/api/jsp/UpdateTea',qs.stringify(params))
      .then(response=>{
        if(response.data.state=='200'){
          this.$router.push(`/admin/TeacherInfoManage`);
        }
      })
      .catch(err=>{
        console.log(err);
      })
    },
    // 跳转更新页面
    toUpdate(){
      this.type = '1';
      if(this.type == '0'){
        this.isCheck = true;
      }else{
        this.isCheck = false;
      }
      this.$router.push(`/TeacherInfo/1/${this.userId}`);
    },
    // 删除
    toDelete(){
      let params = {
        userId:this.userId
      }
      axios.post('/api/jsp/DelTeacher',qs.stringify(params))
      .then(response=>{
        if(response.data.state=='200'){
          this.$router.push(`/admin/TeacherInfoManage`);
        }
      }).catch(err=>{
        console.log(err);
      })
    },
    // 重置密码
    rePassword(){
      let params = {
        userId:this.userId
      }
      axios.post('/api/jsp/RePassword',qs.stringify(params))
      .then(response=>{
        if(response.data.state=='200'){
          this.$router.push(`/admin/TeacherInfoManage`);
        }
      }).catch(err=>{
        console.log(err);
      })
    },
    initTeaPersonal(){
      let params = {
        userId:this.userId
      }
      axios.post('/api/jsp/FindSpeTea',qs.stringify(params))
      .then(response=>{
        //console.log(response.data.datas.user_id);
        let teacherInfo = response.data.datas;
        this.user.userId = teacherInfo.user_id;
        this.user.name = teacherInfo.user_name;
        this.user.sex = teacherInfo.user_sex;
        this.user.email = teacherInfo.user_mail;
        this.user.phone = teacherInfo.user_phone;
        this.user.status = teacherInfo.account_state;
        this.user.userType = teacherInfo.user_identity;
        this.user.password = teacherInfo.user_pwd;

        this.teacher.education = teacherInfo.teacher_bachelor;
        this.teacher.degree = teacherInfo.teacher_degree;
        this.teacher.major = teacherInfo.teacher_major;
      })
      .catch(err=>{
        console.log(err);
      })
    },
    // 学历数据
    initEducation(){
      axios.get('/api/jsp/FindAllEdu')
      .then(response=>{
        console.log(response.data.datas)
        response.data.datas.forEach(element => {
          this.education.push({
            value:element.teacher_bachelor,
            label:element.teacher_bachelor,
          })
        });
        //console.log(this.teacher_bachelor);
      }).catch(err=>{
        console.log(err);
      });
    },
    //学位数据
    initDegree(){
      axios.get('/api/jsp/FindAllDegree')
      .then(response=>{
        // console.log(response.data.datas)
        response.data.datas.forEach(element => {
          this.degree.push({
            value:element.teacher_degree,
            label:element.teacher_degree,
          })
        });
        // console.log(this.teacher_degree);
      }).catch(err=>{
        console.log(err);
      });
    },
    //专业数据
    initMajor(){
      axios.get('/api/jsp/FindAllMajorTeacher')
      .then(response=>{
        // console.log(response.data.datas)
        response.data.datas.forEach(element => {
          this.major.push({
            value:element.major,
            label:element.major,
          })
        });
        // console.log(this.major);
      }).catch(err=>{
        console.log(err);
      });
    }
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
