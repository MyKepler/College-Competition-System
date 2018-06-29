<template>
  <div class="container">
    <el-row type="flex" justify="space-between">
      <el-col :span="15">
        <div class="top" style="border: none;">
          通知公告-内容
        </div>
        <div class="notification-article">
          <div class="article-title">{{title}}</div>
          <el-row class="article-info" type="flex" justify="center">
            <el-col :span="6" v-model="author">作者:{{author}}</el-col>
            <el-col :span="6" v-model="uploadTime">上传时间：{{uploadTime}}</el-col>
          </el-row>
          <el-row class="article" v-html="article">
          </el-row>
          <el-row type="flex" justify="space-between">
            <el-col :span="6" :offset="18" class="article-download">
              <span>下载附件</span>
            </el-col>
          </el-row>
        </div>
      </el-col>
      <el-col :span="6">
        <div class="top">
          <img src="../assets/pic.jpg" class="right-img"/>
          最近招聘
        </div>
        <div class="right-bg">
          <div class="item" v-for="(n,index) in recentNotifications" :key="index" @click="toSignUp(n)">
            <img src="../assets/pic.jpg">
            <span>{{n.publishTime}}</span>
            <p>{{n.notificationTitle}}</p>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import axios from 'axios'
import qs from 'qs'
import {getDate} from './../util/util.js'
export default {
  data() {
    return {
      notificationId: "",
      //url: "/api/front/notifications/notification",
      title: "国务院关于关中平原城市群发展规划的批复",
      author: "教务处",
      uploadTime: "1990-01-01",
      article: "<span>二、《规划》实施要全面贯彻党的十九大精神，以习近平新时代中国特色社会主义思想为指导，统筹推进“五位一体”总体布局和协调推进“四个全面”战略布局，坚持以人民为中心的发展思想，牢固树立和贯彻落实新发展理念</span>",
      recentNotifications: [],
      files: [],
    };
  },
  mounted:function(){
    this.init();
    this.initPro(1,10);
    console.log(this.$route.params.id);
  },
  methods: {
    init(){
      let params={
        id:this.$route.params.id
      }
      axios.post('/api/jsp/NotifyPublishDetails',qs.stringify(params))
      .then(res=>{
        this.title = res.data.datas.title;
        this.author = res.data.datas.userName;
        this.uploadTime = res.data.datas.time;
        this.article = res.data.datas.introduction;
      })
      .catch(err=>{
        console.log(err)
      });
    },
    initPro(pageNum,pageSize){
        let params = {
          // filter: filter, 筛选
          pageNum: pageNum,
          pageSize: pageSize
        }
        axios.post('/api/jsp/QueryAllPublishProjectInfo',qs.stringify(params))
        .then(res=>{
          let pro = res.data.datas;
          // console.log(res)
          pro.forEach(element => {
            this.recentNotifications.push({
              notificationId:element.projectId,
              publishTime:element.publishTime,
              notificationTitle:element.title,
            })
          });
        })
        .catch(err=>{
          console.log(err)
        })
      },
  
    },
    toSignUp(n) {
      // console.log(n);
    }
  
};
</script>
<style scoped>
.wrapper {
  min-height: calc(100vh - 220px);
}
.container {
  height: 100%;
  width: 1140px;
  padding: 0 30px;
  margin: 0 auto;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
  text-align: left;
}
.top {
  height: 80px;
  line-height: 80px;
  border-bottom: 1px solid #5394c5;
}
.notification-article {
  margin-bottom: 100px;
  padding: 0 20px;
  border: 1px solid #cccccc;
  text-align: center;
  font-size: 14px;
  border: 1px solid #000;
}
.article-title {
  height: 60px;
  line-height: 60px;
  color: #000;
  font-size: 18px;
  font-weight: bold;
}
.article-info {
  height: 30px;
  line-height: 30px;
  margin-bottom: 20px;
  /* margin-top: -20px; */
}
.article {
  line-height: 25px;
  text-align: left;
}
.article-form {
  height: 60px;
  line-height: 30px;
  text-align: left;
}
.article-download {
  cursor: pointer;
  height: 60px;
  line-height: 60px;
  color: #5394c5;
  text-align: right
}
.right-img {
  width: 50px;
  height: 50px;
  vertical-align: middle;
  margin-right: 10px;
  margin-bottom: 10px;
}
.right-bg {
  background-color: #f0f0f0;
  margin-top: 20px;
  height: 75%;
}
.item {
  cursor: pointer;
  margin: 0 15px;
  border-bottom: 1px solid #cccccc;
  color: #cccccc;
  font-size: 13px;
  padding: 20px 0 5px 0;
}
.item img {
  width: 30px;
  height: 30px;
  margin-top: -10px;
  vertical-align: middle;
}
.item p {
  padding-left: 5px;
  height: 50px;
  color: #000000;
  font-size: 14px;
  line-height: 20px;
}
</style>
