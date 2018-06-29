<template>
    <div class="wrap">
        <!-- 头部按钮 --> 
        <div class="admin-manage">
            <span style="position:absolute;left:80px">项目管理&nbsp; >&nbsp;项目中期管理</span>
            <el-row>
                <el-col :span="2" :offset="20">
                    <el-button class="exit-filter" size="mini" @click="quitFilter">退出筛选</el-button>
                </el-col>
                <el-col :span="2">
                    <el-button class="filter" size="mini" @click="enterFilter">筛选信息</el-button>
                </el-col>
                <!-- <el-col :span="2">
                    <el-button class="addInfo" type="success" size="mini" @click="enterAdd">添加信息</el-button>
                </el-col> -->
            </el-row>
        </div>
        <!-- 表格table -->
        <el-row class="table">
            <el-table
            :data="tableData"
            stripe
            border
            :row-key="getRowKeys"
            size="mini"
            style="width: 100%;">
            <el-table-column
                type="index"
                width="50"
                :resizable="false">{{tableData.id}}
            </el-table-column>
            <el-table-column
                :label="value"
                :prop="key"
                :resizable="false"
                v-for="(value, key) in keyFormatMap"
                :key='key'
                style="text-align:center;">
            </el-table-column>
            <el-table-column
                label="操作"
                width="200"
                :resizable="false">
                <template scope="scope">
                <el-button
                    size="small"
                    type="primary"
                    @click="handleMore(scope.$index, scope.row)">更多</el-button>
                <!-- <el-button
                    size="small"
                    class="edit-btn"
                    @click="handleEdit(scope.$index, scope.row)">编辑</el-button> -->
                <!-- <el-button
                    size="small"
                    type="danger"
                    @click="handleDelete(scope.$index, scope.row)">删除</el-button> -->
                </template>
            </el-table-column>
            </el-table>
        </el-row>
        <!-- 分页 -->
        <el-row class="page">
            <el-pagination
            @size-change="handleSizeChange"
            @current-change="handleCurrentChange"
            :current-page.sync="currentPage"
            :page-size="pageSize"
            background
            layout="prev, pager, next, jumper"
            :total="totalPage">
            </el-pagination>
        </el-row>
    </div>
</template>
<script>
import axios from 'axios'
import qs from 'qs'
export default {
    data(){
        return{
            //底部分页效果
            //当前页，每页条数，一共多少条
            //后端加载的时候会变化
            currentPage: 1,
            pageSize: 10,
            totalPage: 1000, 
            tableData:[],
            keyFormatMap: {
                // 格式化标签映射表
                title: "项目名称",
                submittime: "提交状态",
                protime:"项目阶段",
                captain: "项目负责人",
                instructor:"指导老师",
            },
            userId: '',
        }
    },
    mounted: function() {
        this.userId = this.$store.state.user.user_id;
        this.initMidPro(this.currentPage,this.pageSize);
    },
    watch:{
        '$route'(to,from){
            this.initMidPro(this.currentPage,this.pageSize);
        }
    },
    methods: {
        initMidPro(pageNum,pageSize){
            let params = {
                pageNum: pageNum,
                pageSize: pageSize,
                userId: this.userId,
            }
            console.log(params);
            axios.post('/api/jsp/FindAllMidProByTea',qs.stringify(params))
            .then(response=>{
            this.tableData = [];
            console.log(response.data.datas);
            response.data.datas.forEach(element => {
            this.tableData.push({
                proId:element.proObj.pro_id,
                title: element.proObj.pro_name,
                submittime:element.proObj.is_submit,
                protime:element.proObj.pro_status,
                captain: element.stuObj.user_name,
                instructor:element.teaObj.user_name,
            })
            });
            }).catch(err=>{
                console.log(err);
            });
            let teaId={
                userId: this.userId
            }
            axios.post('/api/jsp/CountOther',qs.stringify(teaId))
            .then(response=>{
            // this.totalPage = response.data.datas;
            response.data.datas.forEach(element=>{
                if(element.num_name == "教师项目中期"){
                    this.totalPage = element.num;
                    console.log(this.totalPage);
                }
            });
            }).catch(err=>{
                console.log(err);
            });
        },
        getRowKeys(row) {
            return row.id;
        },
        quitFilter(){
            console.log("退出筛选按钮");
        },
        enterFilter(){
            console.log("筛选按钮");
        },
       
        handleMore(index,row){
            // console.log(row);
            this.$router.push(`/TeacherMidManageInfo/${row.proId}`);
            // this.$router.push({path:'Notifyinfo',query:{title:row.title,type:0}});
            console.log("更多按钮");
        },
        handleSizeChange(val) {
            this.pageSize = val;
            this.initMidPro(this.currentPage,this.pageSize);
        // console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.initMidPro(this.currentPage,this.pageSize);
            // console.log(`当前页: ${val}`);
        },
    }
}
</script>
<style>
.admin-manage{
    border-bottom: 1px solid #949494;
    height: 70px;
    margin: 0 80px;
    padding-top: 30px;
    font-size: 18px;
}
/* .addInfo {
  float: right;
  margin-right: 10px;
} */
.filter {
  float: right;
  background-color: #9b59b6;
  color: #ecf0f1;
  outline: 0;
  border: 1px solid #9b59b6;
}
.exit-filter {
  float: right;
  margin-right: 20px;
  background-color: #f19500;
  color: #ecf0f1;
  outline: 0;
  border: 1px solid #f19500;
}
.table{
    margin: 30px 80px;
}
.cell{
    text-align: center;
}
.page{
    padding-top: 20px;
}
.edit-btn {
  background-color: #5cb85c;
  color: #ecf0f1;
  outline: 0;
  border: 1px solid #5cb85c;
}
</style>
