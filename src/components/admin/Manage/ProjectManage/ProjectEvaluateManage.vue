<template>
    <div class="wrap">
         <!-- 头部按钮 --> 
        <div class="admin-manage">
            <span style="position:absolute;left:80px">项目管理&nbsp; >&nbsp;项目评审结果管理</span>
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
                <el-select v-model="scope.row.evaluate"
                            v-if="key === 'evaluate'"
                   @change="initReviewMsg(scope.$index, scope.row)">
                    <el-option v-for="item in valueLabelMap.evaluate"
                    :value="item.value"
                    :label="item.label"
                    :key="item.value">
                    </el-option>
                </el-select>
                <!--如果prop绑定的key！=state则直接现实当前row的key也就是el-table-column中label中的内容-->
                <span v-else>{{scope.row[key]}}</span>
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
                    @click="handleEdit(scope.$index, scope.row)">编辑</el-button>
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
            tableData:[
                {
                    title: "",
                    captain: "",
                    judge:"",
                    score:"" ,
                    proId:"",
                    evaluate:""
                }
            ],
            valueLabelMap: {
            evaluate: [
                {
                value: "申请",
                label: "申请"
                },
                {
                value: "中期",
                label: "中期"
                },
                {
                value: "结题",
                label: "结题"
                }
            ]
            },
            keyFormatMap: {
            // 格式化标签映射表
            title: "项目名称",
            captain: "项目负责人",
            judge:"评审人",
            evaluate:"项目评审阶段",
            score:"评审分数"        
            }
        }
    },
    mounted: function() {
        this.initProRev(this.currentPage,this.pageSize);
    },
    watch:{
        '$route'(to,from){
            this.initProRev(this.currentPage,this.pageSize);
        }
    },
    methods: {
        initProRev(pageNum,pageSize){
                let params = {
                    pageNum: pageNum,
                    pageSize: pageSize
                }
        axios.post('/api/jsp/FindAllProReview',qs.stringify(params))
        .then(response=>{
            this.tableData = [];
            console.log(response.data.datas);
            response.data.datas.forEach(element => {
                this.tableData.push({
                    proId:element.proObj.pro_id,
                    title: element.proObj.pro_name,
                    captain: element.stuObj.user_name,
                    judge:element.prorevObj.review_user_name,
                    score: element.prorevObj.review_code,
                    evaluate:element.proObj.pro_status
                })
            });
        axios.post('/api/jsp/Count')
        .then(response=>{
            // this.totalPage = response.data.datas;
            response.data.datas.forEach(element=>{
                if(element.num_name == "项目评审结果"){
                    this.totalPage = element.num;
                    // console.log(this.totalPage);
                }
            });
        }).catch(err=>{
            console.log(err);
        });
        }).catch(err=>{
            console.log(err);
        });
        },
       initReviewMsg(index,row){
            let params = {
                proId:row.proId,
                proStatus:row.evaluate
            }
            axios.post('/api/jsp/FindRevbytime',qs.stringify(params))
            .then(response=>{
                // 评审信息
                let reviewMsg = response.data.datas;
                if(reviewMsg == undefined){
                    row.score='';
                }else{
                    row.score=reviewMsg.review_code;
                }
            })
            .catch(err=>{
                console.log(err);
            })

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
        // enterAdd(){
        //     console.log("添加按钮");
        // },
        handleMore(index,row){
            this.$router.push(`/EvaluateManageInfo/0/${row.proId}`);
            console.log("更多按钮");
        },
        handleEdit(index,row){
            this.$router.push(`/EvaluateManageInfo/1/${row.proId}`);
            console.log("编辑按钮");
        },
        // handleDelete(){
        //     console.log("删除按钮");
        // },
        handleSizeChange(val) {
            this.pageSize = val;
            this.initProRev(this.currentPage,this.pageSize);
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            this.currentPage = val;
            this.initProRev(this.currentPage,this.pageSize);
            console.log(`当前页: ${val}`);
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
