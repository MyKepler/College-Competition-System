<template>
    <div class="wrap">
        <!-- 头部按钮 --> 
        <div class="admin-manage">
            <span style="position:absolute;left:80px">项目管理&nbsp; >&nbsp;项目评审管理</span>
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
                <template slot-scope="scope">
                <!--如果prop绑定的key！=state则直接现实当前row的key也就是el-table-column中label中的内容-->
                <span>{{scope.row[key]}}</span>
                </template>
            </el-table-column>
            <el-table-column
                label="操作"
                width="240"
                :resizable="false">
                <template scope="scope">
                <el-button
                    size="small"
                    type="primary"
                    @click="handleMore(scope.$index, scope.row)">更多</el-button>
                <el-button
                    size="small"
                    class="edit-btn"
                    @click="handleEdit(scope.$index, scope.row)">评审</el-button>
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
            dialogFormVisible: false,
            isAddPro: true,
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
            //底部分页效果
            //当前页，每页条数，一共多少条
            //后端加载的时候会变化
            currentPage: 1,
            pageSize: 10,
            totalPage: 1000, 
            tableData:[
                {
                    title: "",
                    captain: "",
                    teacher:"",
                    evaluate:"" 
                }
            ],
            keyFormatMap: {
                // 格式化标签映射表
                title: "项目名称",
                captain: "项目负责人",
                teacher:"指导老师",
                evaluate:"项目评审阶段",       
            }
        }
    },
    mounted: function() {
        this.initReviewTask(this.currentPage,this.pageSize,this.userId);
    },
    methods: {
        initReviewTask(pageNum,pageSize,userId){
                let params = {
                    pageNum: pageNum,
                    pageSize: pageSize,
                    userId:"201516"
                }
                axios.post('/api/jsp/FindReviewTask',qs.stringify(params))
                .then(response=>{
                    this.tableData = [];
                    console.log(response.data.datas);
                    response.data.datas.forEach(element => {
                        this.tableData.push({
                            proId:element.proObj.pro_id,
                            title: element.proObj.pro_name,
                            evaluate:element.proObj.pro_status,
                            captain: element.stuObj.user_name,
                            teacher:element.teaObj.user_name,
                        })
                    });
                }).catch(err=>{
                    console.log(err);
                });
                let paramscount = {
                    userId:"201516"
                }
                axios.post('/api/jsp/CountOther',qs.stringify(paramscount))
                .then(response=>{
                    response.data.datas.forEach(element=>{
                    if(element.num_name == "评审端任务"){
                        this.totalPage = element.num;
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
        enterAdd(){
            console.log("添加项目按钮");
        },
        handleMore(index,row){
            this.$router.push(`/ReviewManageInfo/0/${row.proId}`);
            console.log("查看按钮");
        },
        handleEdit(index,row){
            this.$router.push(`/ReviewManageInfo/1/${row.proId}`);
            console.log("评审按钮");
        },
        handleSizeChange(val) {
             this.pageSize = val;
             this.initReviewTask(this.currentPage,this.pageSize,this.userId);
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.initReviewTask(this.currentPage,this.pageSize,this.userId);
        },
    }
}
</script>
<style>
.admin-manage {
    border-bottom: 1px solid #949494;
    height: 70px;
    margin: 0 80px;
    padding-top: 30px;
    font-size: 18px;
}
.addInfo {
  float: right;
}
.filter {
  float: right;
  background-color: #9b59b6;
  color: #ecf0f1;
  outline: 0;
  border: 1px solid #9b59b6;
}
.exit-filter {
  float: right;
  background-color: #f19500;
  color: #ecf0f1;
  outline: 0;
  border: 1px solid #f19500;
}
.table {
    margin: 30px 80px;
}
.cell {
    text-align: center;
}
.page {
    padding-top: 20px;
}
.edit-btn {
  background-color: #5cb85c;
  color: #ecf0f1;
  outline: 0;
  border: 1px solid #5cb85c;
}

</style>
