<template>
  <div class="container">
    <nav-header></nav-header>
    <el-carousel :interval="5000" arrow="outside" indicatorPosition="none" class="carousel">
      <el-carousel-item v-for="item in 4" :key="item">
        <img src="../assets/carousel.jpg" style="width:100%;height:100%;"/>
      </el-carousel-item>
    </el-carousel>
    <!-- <el-row class="inform" type="flex" justify="space-bett">
      <el-col :span="3"  class="notice">通知公告</el-col>
      <el-col :span="20"></el-col>
      <el-button class="filter" @click="dialogFormVisible = true" style="cursor:pointer;">筛选</el-button>
    </el-row> -->
    <!--组件通知-->
    <display-tmpl class='display-tmpl' :title="title" :content='notifications'></display-tmpl>
    <!--分页-->
    <el-pagination
        class="page"
        @size-change="handleSizeChange"
        @current-change="handleCurrentChange"
        :current-page.sync="currentPage"
        :page-size="pageSize"
        layout="prev, pager, next, jumper"
        :total="totalCount">
      </el-pagination>
    </el-row>
    <!--筛选框/组件FilterBox的学习-->
    <el-dialog :visible.sync="dialogFormVisible" class="mydialog" width="30%" center>
      <el-form :model="form">
        <el-form-item label="通知名称">
          <el-input v-model="form.name" auto-complete="off" style="width:280px;"></el-input>
        </el-form-item>
        <el-form-item label="通知日期" >
          <el-date-picker
            v-model="value1"
            type="date"
            placeholder="选择日期" style="width:280px;">
        </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" style="margin-top:-30px;padding-left:180px;">
        <el-button @click="dialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogFormVisible = false">确 定</el-button>
      </div>
    </el-dialog>

    <nav-footer></nav-footer>
  </div>
</template>

<script>
  import NavHeader from  '@/components/NavHeader'
  import NavFooter from  '@/components/NavFooter'
  import DisplayTmpl from "@/components/NormalDisplayTmpl"
  import axios from 'axios'
  import qs from 'qs'
  import {getDate} from './../util/util.js'
  export default {
    components: {
        NavHeader,
        NavFooter,
        DisplayTmpl
    },  
    data(){
      return{
        dialogFormVisible: false,
        form: {
          name: '',
          region: '',
          date1: '',
          date2: '',
          delivery: false,
          type: [],
          resource: '',
          desc: ''
        },
        title: '通知公告',
        notifications:[
          //{
          //   day: '',
          //   yearMonth: '',
          //   title: '',
          //   introduction: ''
          // 在这里必定需要动态生成路由
          // initData() {
          //   axios
          //     .get(this.url, {
          //       params: {
          //         pageNum: this.currentPage,
          //         pageSize: this.pageSize
          //       }
          //     })
          //     .then(res => {
          //       console.log(res);
          //       this.totalCount = res.data.count;
          //       this.notifications = res.data.notifications;
          //       this.notifications.forEach(i => {
          //         i.url = `/notificationsDetail/${i.id}`
          //       })
          //     })
          //     .catch(err => {
          //       console.log(err);
          //     });
          // },}
        ],
        formLabelWidth: '120px',
        value1: '',
        //当前为第一页
        currentPage: 1,
        //一共10页
        //每页显示10条
        pageSize: 5,
        //一共100条数据
        totalCount: 0,
      }
    },
    mounted:function(){
      this.initTableSize();
      this.initNotify(this.currentPage,this.pageSize);
      // this.initPro(this.currentPage,this.pageSize);
    },
    methods: { 
      initNotify(pageNum,pageSize){
        let params = {
          // filter: filter, 筛选
          pageNum: pageNum,
          pageSize: pageSize
        }
        axios.post('/api/jsp/QueryAllPublishNotifyInfo',qs.stringify(params))
        .then(res=>{
          let notifyList = res.data.datas;
          notifyList.forEach(element => {
          this.notifications.push({
              day:element.time.substring(8,11),
              yearMonth:element.time.substring(0,7),
              title:element.title,
              introduction:element.introduction,
              url:element.id
          })
          });
          console.log(this.notifications)
        })
        .catch(err=>{
          console.log(err)
        })
      },
      // initPro(pageNum,pageSize){
      //   let params = {
      //     // filter: filter, 筛选
      //     pageNum: pageNum,
      //     pageSize: pageSize
      //   }
      //   axios.post('/api/jsp/QueryAllPublishProjectInfo',qs.stringify(params))
      //   .then(res=>{
      //     let pro = res.data.datas;
      //     // console.log(res)
      //     // this.notifications=[];
      //     pro.forEach(element => {
      //       this.notifications.push({
      //         day:element.publishTime.substring(8,11),
      //         yearMonth:element.publishTime.substring(0,7),
      //         title:element.title,
      //         introduction:element.introduction,
      //         url:element.projectId
      //       })
      //     });
      //     console.log(this.notifications);
      //   })
      //   .catch(err=>{
      //     console.log(err)
      //   })
      // },
      initTableSize(){
        axios.post('/api/jsp/Count')
        .then(response=>{
          console.log(response.data.datas);
          response.data.datas.forEach(element=>{
              if(element.num_name == "发布公告" || element.num_name == "发布项目"){
                  this.totalCount += element.num;
              }
          });
          this.totalCount = this.totalCount/2;
        }).catch(err=>{
            console.log(err);
        });
      },
      handleSizeChange(val) {
        this.notifications=[];
        console.log(`每页 ${val} 条`);
        this.pageSize = val;
        this.initNotify(this.currentPage,this.pageSize);
        // this.initPro(this.currentPage,this.pageSize);
        
      },
      handleCurrentChange(val) {
        this.notifications=[];
        console.log(`当前页: ${val}`);
        this.currentPage = val;
        this.initNotify(this.currentPage,this.pageSize);
        // this.initPro(this.currentPage,this.pageSize);

      }
    }
  }
</script>

<style scoped>
  .container {
    width: 1000px;
    margin: 70px auto;
    margin-bottom: 0;
  }
  .inform {
    height: 35px;
    margin: 20px auto 0px;
    border-bottom: 2px solid #5394C5;  
  }
  .inform .notice {
    height: 100%;
    background-color: #5394C5;
    line-height: 35px;
    font-size: 16px;
    color: white;
  }
  .inform .filter {
    height: 100%;
    background-color: #5394C5;
    font-size: 16px;
    color: white;
    border-radius: 5px;   
  }
  .production {
    width: 900px;
    height: 110px;
    margin: auto;
    border-bottom:1px solid #7f8a99;
  }
  .production .time {
    height: 80%;
    line-height: 60px;
    color: #5394C5;
  }
  .production .details {
    height: 80%;
    text-align: left;
    word-wrap: break-word; 
    word-break: normal;
    padding: 15px 20px 15px 5px;
    font-size: 14px;
  }
  .pagination {
    padding: 30px;
  }
  .mydialog {
    position: fixed;
    top: 200px;
    padding: 0;
  }
  .page {
    margin: 20px 0;
  }
</style>