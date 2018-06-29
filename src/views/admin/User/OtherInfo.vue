<template>
  <div class="container">
    <h2 class="title">评审专家信息管理</h2>
    <div class="head-info-wrapper">
      <div>
        <span>用户管理 > </span>
        <span v-if= "type==='0'">评审专家信息查看</span>
        <span v-if= "type==='1'">评审专家信息修改</span>
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
            <span class="item-name" >姓名</span>
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
            <div class="item-content" >
              <el-select v-model="user.userType" disabled>
                
              </el-select>
            </div>
          </el-col>
          <el-col :span="8">
            <span class="item-name" >密码</span>
            <div class="item-content"  v-if= "type==='0'">
              <el-input type="password" v-model="user.password" :disabled="isCheck"></el-input>
            </div>
            <div class="item-content"  v-if= "type==='1'">
              <el-button type="danger" size="small" @click="rePassword">重置密码</el-button>
            </div>
          </el-col>
        </el-row>
      </div>
      <div class="info-student">
        <div class="message-title">
          <span>评审专家附加信息</span>
        </div>
        <el-row>
          <el-col :span="8">
            <span class="item-name">学历</span>
            <div class="item-content">
              <el-select v-model="teacher.education" :disabled="isCheck">
                <el-option
                  v-for="item in education"
                  :key="item.key"
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
                  :key="item.key"
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
                  :key="item.key"
                  :label="item.label"
                  :value="item.value">
                </el-option>
              </el-select>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <span class="item-name">企业名称</span>
            <div class="item-content">
              <el-input v-model="company.name" :disabled="isCheck"></el-input>
            </div>
          </el-col>
          <el-col :span="8">
            <span class="item-name">企业电话</span>
            <div class="item-content">
              <el-input v-model="company.phone" :disabled="isCheck"></el-input>
            </div>
          </el-col>
          <el-col :span="8">
            <span class="item-name" >企业负责人</span>
            <div class="item-content">
              <el-input v-model="company.leader" :disabled="isCheck"></el-input>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="16">
            <span class="item-name" >企业地址</span>
            <div class="item-content">
              <el-input style="width: 92.5%;" v-model="company.address" :disabled="isCheck"></el-input>
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
      teacher: {
        education: '',
        degree: '',
        major: ''
      },
      education: [],
      degree: [],
      major: [],
      company: {
        name: '',
        phone: '',
        leader: '',
        address: ''
      },
      type: '0',   //0为查看通知；1为修改通知
      isCheck: true,
      userId: '',
    }
  },
  mounted:function(){
    // 获取路由上的type参数
    this.type = this.$route.params.type;
    this.userId = this.$route.params.userId;
    console.log(this.userId);
    console.log(this.type);
    this.initReviewerPersonal();
    if(this.type == '0'){
      this.isCheck = true;
    }else{
      this.isCheck = false;
      this.initEducation();
      this.initDegree();
      this.initMajor();
    }
    //this.isCheck = this.type == '0'?true:false
    //this.$router.push(`/OtherInfo/1/${this.userId}`);

  },
  components: {

  },
  methods: {
    //跳转到该页面时的初始化
    initReviewerPersonal(){
      let params = {
        userId:this.userId
      }
      axios.post('/api/jsp/FindReviewerById',qs.stringify(params))
      .then(response=>{
        // console.log(response.data.datas);
        let reviewerInfo = response.data.datas;
        this.user.userId = reviewerInfo.user_id;
        this.user.name = reviewerInfo.user_name;
        this.user.sex = reviewerInfo.user_sex;
        this.user.email = reviewerInfo.user_mail;
        this.user.phone = reviewerInfo.user_phone;
        this.user.status = reviewerInfo.account_state;
        this.user.userType = reviewerInfo.user_identity;
        this.user.password = reviewerInfo.user_pwd;

        this.teacher.education = reviewerInfo.teacher_bachelor;
        this.teacher.degree = reviewerInfo.teacher_degree;
        this.teacher.major = reviewerInfo.teacher_major;

        this.company.name = reviewerInfo.company_name;
        this.company.phone = reviewerInfo.company_phone;
        this.company.leader = reviewerInfo.company_principal;
        this.company.address = reviewerInfo.company_address;
      })
      .catch(err=>{
        console.log(err);
      })
    },
    // 跳转到编辑页面
    toUpdate(){
      this.type = '1';
      if(this.type == '0'){
        this.isCheck = true;
      }else{
        this.isCheck = false;
      }
      this.$router.push(`/OtherInfo/1/${this.userId}`);
    },
    // 删除按钮
    toDelete(){
      let params = {
        userId:this.userId
      }
      axios.post('/api/jsp/DeleteReviewer',qs.stringify(params))
      .then(response=>{
        if(response.data.state=='200'){
          this.$router.push(`/admin/OtherInfoManage`);
        }
      }).catch(err=>{
        console.log(err);
      })
    },
    // 取消修改
    cancel(){
      this.initReviewerPersonal();
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
        education: this.teacher.education,
        degree: this.teacher.degree,
        major: this.teacher.major,
        companyName: this.company.name,
        companyPhone: this.company.phone,
        companyLeader: this.company.leader,
        companyAddress: this.company.address
      }
      console.log(params)
      axios.post('/api/jsp/UpdateReviewer',qs.stringify(params))
      .then(response=>{
        if(response.data.state=='200'){
          this.$router.push(`/admin/OtherInfoManage`);
        }
      })
      .catch(err=>{
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
          this.$router.push(`/admin/OtherInfoManage`);
        }
      }).catch(err=>{
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
