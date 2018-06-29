<template>
  <div class="container">
    <h2 class="title">项目申请管理</h2>
    <div class="head-info-wrapper">
      <div>
        <span>项目管理 > </span>
        <span class="add">项目申请添加管理</span>
      </div>
      <div class="btn-wrap">
        <el-button type="primary" plain size="small" @click="cancel">取消添加</el-button>
        <el-button type="warning" plain size="small" @click="affirm">确认添加</el-button>
      </div>
    </div>
    <div class="info-wrapper">
      <div class="message-project">
        <el-row>
          <el-col :span="6">
            <div class="block">
              <span class="item-name">项目名称</span>
              <div class="item-content">
              <el-input placeholder="请输入项目名称" v-model="project.name"></el-input>
            </div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="block">
              <span class="item-name">阶段</span>
              <div class="item-content">
                  <el-select v-model="project.status" disabled></el-select>
              </div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="block">
              <span class="item-name">已报名人数（人）</span>
              <div class="item-content">
                <el-input disabled placeholder="请输入已报名人数" v-model="project.currentNum"></el-input>
              </div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="block">
              <span class="item-name">计划报名人数（人）</span>
              <div class="item-content">
                <el-input placeholder="请输入计划报名人数" v-model="project.planNum"></el-input>
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div class="block">
              <span class="item-name">申请时间</span>
              <div class="item-content">
                <el-date-picker
                  v-model="project.startTime"
                  type="date"
                  value-format="yyyy-MM-dd"
                  :picker-options="pickerOptions1">
                </el-date-picker>
              </div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="block">
              <span class="item-name">结束时间</span>
              <div class="item-content">
                <el-date-picker
                  v-model="project.finishTime"
                  type="date"
                  value-format="yyyy-MM-dd"
                  :picker-options="pickerOptions2">
                </el-date-picker>
              </div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="block">
              <span class="item-name">负责人</span>
              <div class="item-content">
                <el-input @blur="initStu"   placeholder="请输入项目负责学号" v-model="project.leader"></el-input>
              </div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="block">
              <span class="item-name">指导老师</span>
              <div class="item-content">
                <el-input @blur="initTea"   placeholder="请输入指导老师工号" v-model="project.counselor" disabled></el-input>
              </div>
            </div>
          </el-col>
        </el-row>  
      </div>
      <div class="message-members">
        <div class="message-title">
          <span>负责人信息</span>
        </div>
        <div class="message-wrapper">
          <div class="message-wrapper-item">
            <span>负责人</span>
            <el-button class="btn-check" type="primary" size="small" @click="toStuDetail">详情查看</el-button>
            <div class="itemshow">
              <el-row>
                <el-col :span="6">
                  <div class="block">
                    <span class="item-name">学号</span>
                    <div class="item-content">
                      <el-input disabled v-model="leader.userId"></el-input>
                    </div>
                  </div>
                </el-col>
                <el-col :span="6">
                  <div class="block">
                    <span class="item-name">姓名</span>
                    <div class="item-content">
                      <el-input disabled v-model="leader.name"></el-input>
                    </div>
                  </div>
                </el-col>
                <el-col :span="6">
                  <div class="block">
                    <span class="item-name">邮箱</span>
                    <div class="item-content">
                      <el-input disabled v-model="leader.email"></el-input>
                    </div>
                  </div>
                </el-col>
                <el-col :span="6">
                  <div class="block">
                    <span class="item-name">手机号</span>
                    <div class="item-content">
                      <el-input disabled v-model="leader.userPhone"></el-input>
                    </div>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
          <div class="message-wrapper-item">
            <span>指导老师</span>
            <el-button class="btn-check" type="primary" size="small" @click="toTeaDetail">详情查看</el-button>
            <div class="itemshow">
              <el-row>
                <el-col :span="6">
                  <div class="block">
                    <span class="item-name">工号</span>
                    <div class="item-content">
                      <el-input disabled v-model="teacher.userId"></el-input>
                    </div>
                  </div>
                </el-col>
                <el-col :span="6">
                  <div class="block">
                    <span class="item-name">姓名</span>
                    <div class="item-content">
                      <el-input disabled v-model="teacher.name"></el-input>
                    </div>
                  </div>
                </el-col>
                <el-col :span="6">
                  <div class="block">
                    <span class="item-name">邮箱</span>
                    <div class="item-content">
                      <el-input disabled v-model="teacher.email"></el-input>
                    </div>
                  </div>
                </el-col>
                <el-col :span="6">
                  <div class="block">
                    <span class="item-name">手机号</span>
                    <div class="item-content">
                      <el-input disabled v-model="teacher.userPhone"></el-input>
                    </div>
                  </div>
                </el-col>
              </el-row>
            </div>
          </div>
        </div>
      </div>
      <div class="message-file">
        <div class="message-title">
          <span>附件</span>
        </div>
        <el-row>
          <el-col :span="16">
            <div class="block">
              <div class="item-content">
                <el-upload
                  class="upload-demo"
                  ref="upload"
                  action="/api/jsp/ProjectSignUpFileUpload"
                  :on-success="handleSuccess"
                  :on-preview="handlePreview"
                  :on-remove="handleRemove"
                  :file-list="fileList"
                  :data="fromData"
                  :auto-upload="false">
                  <el-button slot="trigger" size="small" type="primary">选取附件</el-button>
                  <el-button style="margin-left: 10px;" size="small" type="success" @click="submitUpload">上传到服务器</el-button>
                  <el-button style="margin-left: 10px;"   size="small" type="danger" >删除全部</el-button>
                </el-upload>
              </div>
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
          // return time.getTime() > Date.now();
        }
      },
      pickerOptions2: {
        disabledDate(time) {
          // return time.getTime() > Date.now();
        }
      },
      project: {
        name: "",
        status: '申请',
        currentNum: '0',
        planNum: '',
        startTime: '',
        finishTime:'',
        leader: '',
        counselor:'',
      },
      leader: {
        userId: "",
        name: "",
        email: "",
        userPhone: ""
      },
      teacher: {
        userId: "",
        name: "",
        email: "",
        userPhone: ""
      },
      fileList: [
            // {name: '', url: ''}
      ], type: '1',   //1为添加通知管理；2为修改通知；3为查看通知
      proId:'',
      fromData:{
        id: 'yhcj',
        type: '申请',
        state: '可用',
        time: getDate(),
      },
      isUpload: false,
    }
  },
  mounted:function(){
    this.project.counselor = this.$store.state.user.user_id;
    this.initTea();
  },
  methods: {
    getId(){
      if(!this.isUpload){
        this.fromData.id = new Date().getTime();
        this.proId = this.fromData.id;
        console.log(this.fromData.id);
      }
    },
    handleSuccess(res,file,fileList){
      this.fromData.id = res.datas;
      console.log(this.fromData.id);
    },
    submitUpload() {
      this.isUpload = true;
      this.$refs.upload.submit();
    },
    handleRemove(file, fileList) {
      console.log(file, fileList);
    },
    handlePreview(file) {
      console.log(file);
    },
    // 查看学生详细信息
    toStuDetail(){
      this.$router.push(`/StudentInfo/0/${this.leader.userId}`);
    },
    // 查看老师详细信息
    toTeaDetail(){
      this.$router.push(`/TeacherInfo/0/${this.teacher.userId}`);
    },
    // 确认添加
    affirm(){
      // this.proId = new Date().getTime();
      // console.log(this.proId);
      this.getId();
      let params ={
        proId: this.fromData.id,
        proName: this.project.name,
        counselor: this.project.counselor,
        leader: this.project.leader,
        proPlan: this.project.planNum,
        startYear: this.project.startTime,
        finishYear: this.project.finishTime,
      }
      
      axios.post('/api/jsp/InsertPro',qs.stringify(params))
      .then(response=>{
        if(response.data.state=='200'){
          this.initTea();
          this.initStu();
          this.$router.push(`teacher/TeacherProjectSignUp`);
        }
      })
      .catch(err=>{
        console.log(err);
      })
    },
    initStu(){
      console.log(this.project.leader)
      let params ={
        userId: this.project.leader
      }
      axios.post('/api/jsp/FindSpeStu',qs.stringify(params))
      .then(response=>{
        // console.log(response)
        let stu = response.data.datas;
        if(stu == undefined){
          this.leader.userId = "暂无此人信息";
          this.leader.name =  "暂无此人信息";
          this.leader.email =  "暂无此人信息";
          this.leader.userPhone =  "暂无此人信息";
        }else{
          this.leader.userId = stu.user_id;
          this.leader.name = stu.user_name;
          this.leader.email = stu.user_mail;
          this.leader.userPhone = stu.user_phone;
        }
      })
      .catch(err=>{
        console.log(err);
      })
    },
    initTea(){
      console.log(this.project.counselor)
      let params ={
        userId: this.project.counselor,
      }
      axios.post('/api/jsp/FindSpeTea',qs.stringify(params))
      .then(response=>{
        // console.log(response)
        let tea = response.data.datas;
        if(tea == undefined){
          this.teacher.userId = "暂无此人信息";
          this.teacher.name =  "暂无此人信息";
          this.teacher.email =  "暂无此人信息";
          this.teacher.userPhone =  "暂无此人信息";
        }else{
          this.teacher.userId = tea.user_id;
          this.teacher.name = tea.user_name;
          this.teacher.email = tea.user_mail;
          this.teacher.userPhone = tea.user_phone;
        }
      })
      .catch(err=>{
        console.log(err);
      })
    },
    // 取消添加
    cancel(){
      this.$router.push(`teacher/TeacherProjectSignUp`);
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
  margin: 30px 0 0 0;
}
.message-wrapper-item{
  margin-top: 20px;
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
</style>
