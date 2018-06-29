<template>
  <div class="container">
    <h2 class="title">项目申请管理</h2>
    <div class="head-info-wrapper">
      <div>
        <span>项目管理 > </span>
        <span>项目申请查看管理</span>
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
            <span class="item-name">状态</span>
            <div class="item-content">
                <el-select v-model="project.status" disabled>
                  <el-option
                    v-for="item in options2"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">已报名人数（人）</span>
            <div class="item-content">
              <el-input disabled placeholder="请输入已报名人数" v-model="project.currentNum"></el-input>
            </div>
          </el-col>
          <el-col :span="6">
            <span class="item-name">计划报名人数（人）</span>
            <div class="item-content">
              <el-input placeholder="请输入计划报名人数" v-model="project.planNum" disabled></el-input>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div class="block">
              <span class="item-name">申请时间</span>
              <div class="item-content">
                <el-date-picker
                  disabled
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
                  disabled
                  v-model="project.finishTime"
                  value-format="yyyy-MM-dd"
                  type="date"
                  :picker-options="pickerOptions1">
                </el-date-picker>
              </div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="block">
              <span class="item-name">负责人</span>
              <div class="item-content">
                <el-input placeholder="请输入项目负责人" v-model="project.leader" disabled></el-input>
              </div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="block">
              <span class="item-name">指导老师</span>
              <div class="item-content">
                <el-input placeholder="请输入指导老师" v-model="project.counselor" disabled></el-input>
              </div>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <div class="block">
              <span class="item-name">评审人</span>
              <div class="item-content">
                <el-input placeholder="请输入评审人" v-model="review.userId" disabled></el-input>
              </div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="block">
              <span class="item-name">评审人姓名</span>
              <div class="item-content">
                <el-input placeholder="请输入评审人姓名" v-model="review.name" disabled></el-input>
              </div>
            </div>
          </el-col>
          <el-col :span="6">
            <div class="block">
              <span class="item-name">评审分数</span>
              <div class="item-content">
                <el-input placeholder="请输入评审分数" v-model="review.revieScore" disabled></el-input>
              </div>
            </div>
          </el-col>
        </el-row>
      </div>
      <!-- <div class="message-members">
        <div class="message-title">
          <span>负责人/指导老师信息</span>
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
      </div> -->
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
      options2: [{
        value: '申请',
        label: '申请'
      }],
      pickerOptions1: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
      pickerOptions2: {
        disabledDate(time) {
          return time.getTime() > Date.now();
        }
      },
      project: {
        name: "",
        status: '申请',
        currentNum: '',
        planNum: '',
        startTime: '',
        finishTime:'',
        leader: '',
        counselor:'',
      },
      review:{
        userId:'',
        name:"",
        revieScore:""
      },
      fileList: [
        // {name: '', url: ''}
      ],
      type: '0',   //0为查看通知；1为修改通知
      isCheck: true,
      proId: '',
      isUpload: false,
    }
  },
  mounted:function(){
    // 获取路由上的type参数
    this.type = this.$route.params.type;
    this.proId = this.$route.params.proId;
    this.initProPersonal();
    this.initFileList();
    if(this.type == '0'){
      this.isCheck = true;
    }else{
      this.isCheck = false;
    }
  },
  computed:{
    fromData:function(){
      return {
        id: this.proId,
        time: getDate(),
        state:'可用',
        type:'申请',
      };
    }
  },
  methods: {
    initProPersonal(){
      let params = {
        proId:this.proId
      }
      axios.post('/api/jsp/FindSpePro',qs.stringify(params))
      .then(response=>{

        let proObj = response.data.datas.proObj;
        let stuObj = response.data.datas.stuObj;
        let teaObj = response.data.datas.teaObj;
        let prorevObj = response.data.datas.prorevObj;
        let reviewerObj = response.data.datas.reviewerObj;
        // 项目信息
        this.project.name = proObj.pro_name;
        this.project.status = proObj.pro_status;
        this.project.currentNum = proObj.act_num;
        this.project.planNum = proObj.plan_num;
        this.project.startTime = proObj.start_year;
        this.project.finishTime = proObj.finish_year;
        this.project.leader = proObj.pro_principal_id;
        this.project.counselor = proObj.pro_teacher_id;
        if(prorevObj == undefined){
          this.review.userId = '暂无评审信息';
          this.review.revieScore = '暂无评审信息';
          this.review.name = '暂无评审信息';
        } else{
          // 评审信息
          this.review.userId = prorevObj.id;
          this.review.revieScore = prorevObj.review_code;
          this.review.name = reviewerObj.user_name;
        }
      })
      .catch(err=>{
        console.log(err);
      })
    },
    initFileList(){
      let params = {
         id: this.proId,
         type:'申请',
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
      window.open("http://localhost:8080/jsp/ProjectMangeFileDownload?type=申请&id="+this.proId);
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
