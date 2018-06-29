<template>
    <div class="wrap">
         <!-- 头部按钮 --> 
        <div class="admin-manage">
            <span style="position:absolute;left:80px">我要报名</span>
            <el-row>
                <el-col :span="2" :offset="22">
                    <el-button class="addInfo" type="success" size="mini" @click="enterAdd">我要报名</el-button>
                </el-col>
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
                width="200"
                :resizable="false">
                <template scope="scope">
                <el-button
                    size="small"
                    type="primary"
                    @click="handleMore(scope.$index, scope.row)">查看</el-button>
                <!-- <el-button
                    size="small"
                    class="edit-btn"
                    @click="handleDown(scope.$index, scope.row)">下载材料</el-button> -->
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
            totalPage: 10, 
            tableData:[],
            keyFormatMap: {
                // 格式化标签映射表
                title: "项目名称",
                captain: "项目负责人",
                teacher:"指导老师",
                pass: "审核通过",       
            },
            userId:"",

        }
    },
    mounted: function() {
        this.userId = this.$store.state.user.user_id;
        this.initAudit();
        console.log( this.userId)
    },   
    watch:{
        '$route'(to,from){
            this.initAudit();
        }
    },
    methods: {
        initAudit(pageNum,pageSize){
            let params = {
                // filter: filter, 筛选
                // pageNum: pageNum,
                // pageSize: pageSize
                userId:this.userId
            }
            console.log(params);
            axios.post('/api/jsp/StudentSignUpMyJoin',qs.stringify(params))
            .then(response=>{
                this.tableData = [];
                console.log(response.data.datas);
                response.data.datas.forEach(element => {
                    this.tableData.push({
                        id:element.projectId,
                        title:element.projectName,
                        stu_id:element.projectPrincipalId,
                        captain:element.projectPrincipalName,
                        teacher:element.projectTeacherName,
                        pass:element.state,
                    })
                });
            }).catch(err=>{
                console.log(err);
            });
        },
        getRowKeys(row) {
            return row.id;
        },
        enterAdd(index,row){
            this.$router.push(`/StudentAuditAdd`);
            console.log("添加按钮");
        },
        handleMore(index,row){
            this.$router.push(`/StudentAudit/${this.userId}/${row.id}`);
            console.log("更多按钮");
        },
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
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
/* .filter {
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
} */
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
