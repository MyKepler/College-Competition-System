<template>

  <div class="info-wrapper">
    <div class="avatar-wrapper">
      <img src="@/assets/pic.jpg" alt="用户头像" class="avatar">
      <span class="edit-avatar">修改头像</span>
    </div>
    <div class="teacher-info">
      <header class="title">
        <span class="title-name">我的资料</span>
        <div class="operate-area">
          <!-- <span class="operate save" @click="editInfo">确认修改</span> -->
          <span class="operate save" @click="update">确认修改</span>
          <span class="operate exit" @click="exit">退出</span>
        </div>
      </header>
      <div class="info-content">
        <div class="info-item">
          姓<span class="fill-text">填充</span>名：{{info.user_name}}
        </div>
        <div class="info-item">
          性<span class="fill-text">填充</span>别：{{info.user_sex}}
        </div>
        <div class="info-item">
          工<span class="fill-text">填充</span>号：{{info.user_id}}
        </div>
        <div class="info-item">
          <div class="key">
            邮<span class="fill-text">填充</span>箱：
          </div>
          <div class="value">
            <el-input v-model="info.user_email"></el-input>
          </div>
        </div>
        <div class="info-item">
          <div class="key">
            手机号码：
          </div>
          <div class="value">
            <el-input v-model="info.user_phone"></el-input>
          </div>

        </div>
         <div class="info-item">
          <div class="key">
            密<span class="fill-text">填充</span>码：
          </div>
          <div class="value">
            <el-input type="password" v-model="info.user_pwd"></el-input>
          </div>
        </div>
        <div class="info-item" v-if="type == '教师' || type == '评审专家'">
          学<span class="fill-text">填充</span>历：{{info.teacher_bachelor}}
        </div>
        <div class="info-item" v-if="type == '教师' || type == '评审专家'">
          学<span class="fill-text">填充</span>位：{{info.teacher_degree}}
        </div>
        <div class="info-item" v-if="type == '教师' || type == '评审专家'">
          专<span class="fill-text">填充</span>业：{{info.teacher_major}}
        </div>
        <div class="info-item" v-if="type == '评审专家'">
          <div class="key">
            企业名称：{{info.company_name}}
          </div>
          <!-- <div class="value">
            <el-input v-model="info.company_name"></el-input>
          </div> -->
        </div>
        <div class="info-item" v-if="type == '评审专家'">
          <div class="key">
            企业电话：{{info.company_phone}}
          </div>
          <!-- <div class="value">
            <el-input v-model="info.company_phone"></el-input>
          </div> -->
        </div>
        <div class="info-item" v-if="type == '评审专家'">
          <div class="key">
            负责人员：{{info.company_principal}}
          </div>
          <!-- <div class="value">
            <el-input v-model="info.company_principal"></el-input>
          </div> -->
        </div>
        <div class="info-item" v-if="type == '评审专家'">
          <div class="key">
            企业地址：{{info.company_address}}
          </div>
          <!-- <div class="value">
            <el-input v-model="info.company_address"></el-input>
          </div> -->
        </div>
        <div class="info-item">
          用户状态：{{info.account_state}}
        </div>

      </div>
    </div>
  </div>

</template>
<script>
import axios from 'axios'
import qs from 'qs'
export default {
  data() {
    return {
      info: {
        user_name: '',
        user_sex: '',
        user_id: '',
        user_email: '',
        user_phone: '',
        user_pwd: '',
        account_state: '',
        teacher_bachelor: '' ,
        teacher_degree: '',
        teacher_major: '',
        company_name: '',
        company_phone: '',
        company_principal: '',
        company_address: ''
      },
      type: ''
    };
  },
  mounted:function(){
    // 获取路由上的type参数
    this.type = this.$store.state.user.user_identity;
    this.info.user_id = this.$route.params.userId;
    this.initUserInfo();

  },
  methods: {
    initUserInfo(){
      let params={
        userId: this.info.user_id,
        type: this.type,
      }
      console.log(params);
      axios.post('/api/jsp/FindChangeInfoByType',qs.stringify(params))
      .then(response=>{
        console.log(response.data.datas);
        let userObj = response.data.datas.userObj;
        let teaObj = response.data.datas.teaObj;
        let revObj = response.data.datas.revObj;

        // let teacherInfo = response.data.datas;
        this.info.user_name = userObj.user_name;
        this.info.user_id = userObj.user_id;
        this.info.user_email = userObj.user_mail;
        this.info.user_phone = userObj.user_phone
        this.info.user_sex = userObj.user_sex;
        this.info.user_pwd = userObj.user_pwd;
        this.info.account_state = userObj.account_state;

        if(this.type == '教师'){
          this.info.teacher_degree = teaObj.teacher_degree;
          this.info.teacher_bachelor = teaObj.teacher_bachelor;
          this.info.teacher_major = teaObj.teacher_major;
        }
        else{
          this.info.teacher_degree = revObj.teacher_degree;
          this.info.teacher_bachelor = revObj.teacher_bachelor;
          this.info.teacher_major = revObj.teacher_major;

          this.info.company_name = revObj.company_name,
          this.info.company_phone = revObj.company_phone,
          this.info.company_principal = revObj.company_principal,
          this.info.company_address = revObj.company_address
        }

        // this.info.account_state = teacherInfo.account_state;
      })
      .catch(err=>{
        console.log(err);
      })
    },
    update(){
      let params = {
        userId: this.info.user_id,
        email: this.info.user_email,
        phone: this.info.user_phone,
        user_pwd: this.info.user_pwd
      }
      console.log(params);
      axios.post('/api/jsp/UpdateChangeInfoByTea',qs.stringify(params))
      .then(response=>{
        if(response.data.state=='200'){
          if(this.type == "教师"){
            this.$router.push({path: '/teacher'})
          }else if(this.type == "评审专家") {
            this.$router.push({path: '/review'})
          }else if(this.type == "学生"){
            this.$router.push({path: '/student'})
          }
          // console.log("更新成功");
        }
      })
      .catch(err=>{
        console.log(err);
      })
    },
    exit(){
      if(this.type == "教师"){
        this.$router.push({path: '/teacher'})
      }else if(this.type == "评审专家") {
        this.$router.push({path: '/review'})
      }else if(this.type == "学生"){
        this.$router.push({path: '/student'})
      }
    }
  }
};
</script>
<style scoped>
.info-wrapper {
  width: 85%;
  margin: 5rem auto;
  padding: 4rem;
  border-radius: 1rem;
  background-color: #e4e4e4;
  display: flex;
}

.avatar-wrapper {
  display: inline-block;
}

.avatar {
  width: 120px;
  height: 120px;
  display: block;
  border-radius: 50%;
  padding: 0.3rem;
  border: 2px dashed #ccc;
}

.edit-avatar {
  display: block;
  text-align: center;
  padding: 0.5rem;
  font-size: 0.8rem;
  color: #4586ff;
  cursor: pointer;
}

.teacher-info {
  width: 80%;
  margin-left: 4rem;
}

.title {
  display: flex;
  justify-content: space-between;
  padding: 0.2rem 1rem 1rem 1rem;
  border-bottom: 2px dashed #ccc;
}

.title span.title-name {
  font-size: 1.4rem;
}

.title div.operate-area {
  display: flex;
  align-items: flex-end;
}
.title span.operate {
  margin-right: 1rem;
  font-size: 1rem;
  color: #4586ff;
  cursor: pointer;
}

.fill-text {
  color: transparent;
}

.info-item {
  display: flex;
  align-items: center;
  padding: 1.5rem 0 0 3rem;
}

.value {
  width: 400px;
}
</style>
