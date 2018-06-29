import Vue from 'vue'
import Router from 'vue-router'

import Login from '@/views/LoginAndReg'
import Index from '@/views/Index'
import FileShow from '@/views/FileShow'

// 管理员端
import Admin from '@/views/Admin'
import Notification from '@/components/admin/Manage/NotificationsManage/Notification'
import ProjectSignUp from '@/components/admin/Manage/ProjectManage/ProjectSignUp'
import ProjectAudit from '@/components/admin/Manage/ProjectManage/ProjectAudit'
import ProjectEndManage from '@/components/admin/Manage/ProjectManage/ProjectEndManage'
import ProjectMidManage from '@/components/admin/Manage/ProjectManage/ProjectMidManage'
import ProjectMemberManage from '@/components/admin/Manage/ProjectManage/ProjectMemberManage'
import ProjectInfoManage from '@/components/admin/Manage/ProjectManage/ProjectInfoManage'
import ProjectEvaluateManage from '@/components/admin/Manage/ProjectManage/ProjectEvaluateManage'
import EvaluationExperts from '@/components/admin/Manage/EvalustionManage/EvaluationExperts'
import UserInfoManage from '@/components/admin/Manage/UserManage/UserInfoManage'
import TeacherInfoManage from '@/components/admin/Manage/UserManage/TeacherInfoManage'
import StudentInfoManage from '@/components/admin/Manage/UserManage/StudentInfoManage'
import OtherInfoManage from '@/components/admin/Manage/UserManage/OtherInfoManage'

// 教师端
import Teacher from '@/views/Teacher'
import TeacherMyInfo from '@/components/teacher/Manage/TeacherMyInfo'
import TeacherProjectSignUp from '@/components/teacher/Manage/ProjectManage/ProjectSignUp'
import TeacherProjectEndManage from '@/components/teacher/Manage/ProjectManage/ProjectEndManage'
import TeacherProjectMidManage from '@/components/teacher/Manage/ProjectManage/ProjectMidManage'
import TeacherProjectMemberManage from '@/components/teacher/Manage/ProjectManage/ProjectMemberManage'
import TeacherProjectInfoManage from '@/components/teacher/Manage/ProjectManage/ProjectInfoManage'
import TeacherProjectEvaluateManage from '@/components/teacher/Manage/ProjectManage/ProjectEvaluateManage'
import TeacherProjectMaterialManage from '@/components/teacher/Manage/ProjectManage/ProjectMaterialManage'

// 学生端
import Student from '@/views/Student'
import StudentMyInfo from '@/components/student/Manage/StudentMyInfo'
// 学生端查看
import StudentProjectSignUp from '@/components/student/Manage/ProjectCheck/ProjectSignUp'
import StudentProjectEndCheck from '@/components/student/Manage/ProjectCheck/ProjectEndManage'
import StudentProjectMidCheck from '@/components/student/Manage/ProjectCheck/ProjectMidManage'
import StudentProjectCheck from '@/components/student/Manage/ProjectCheck/ProjectInfoManage'
import StudentProjectEvaluateCheck from '@/components/student/Manage/ProjectCheck/ProjectEvaluateManage'
// 学生端管理
import StudentProjectInfoManage from '@/components/student/Manage/ProjectManage/ProjectInfoManage'
import StudentProjectMaterialManage from '@/components/student/Manage/ProjectManage/ProjectMaterialManage'
import StudentProjectMemberManage from '@/components/student/Manage/ProjectManage/ProjectMemberManage'
import AuditManage from '@/components/student/Manage/Audit/AuditManage'
// 评审专家端
import Review from '@/views/Review'
import ReviewMyInfo from '@/components/review/Manage/ReviewMyInfo'
import ReviewTask from '@/components/review/Manage/ReviewTask'

// 管理员详情页面

// 通知公告详情
import Notifyinfo from '@/views/admin/Notifications/Notifyinfo'
import NotifyinfoAdd from '@/views/admin/Notifications/NotifyinfoAdd'
// 项目管理详情
import EndManageInfo from '@/views/admin/Project/EndManageInfo'
import MidManageInfo from '@/views/admin/Project/MidManageInfo'
import EvaluateManageInfo from '@/views/admin/Project/EvaluateManageInfo'
import ProjectInfo from '@/views/admin/Project/ProjectInfo'
import SignUpInfo from '@/views/admin/Project/SignUpInfo'
import SignUpInfoAdd from '@/views/admin/Project/SignUpInfoAdd'
import ProjectAuditInfo from '@/views/admin/Project/ProjectAuditInfo'
import ProjectMemberInfo from '@/views/admin/Project/ProjectMemberInfo'
// 用户管理详情
import UserInfo from '@/views/admin/User/UserInfo'
import TeacherInfo from '@/views/admin/User/TeacherInfo'
import StudentInfo from '@/views/admin/User/StudentInfo'
import OtherInfo from '@/views/admin/User/OtherInfo'

// 个人修改界面
import ChangeInfo from '@/components/ChangeInfo'

// 教师详情页面
import TeacherSignUpInfo from '@/views/teacher/Project/SignUpInfo'
import TeacherSignUpInfoAdd from '@/views/teacher/Project/SignUpInfoAdd'
import TeacherMidManageInfo from '@/views/teacher/Project/MidManageInfo'
import TeacherEndManageInfo from '@/views/teacher/Project/EndManageInfo'
import TeacherProjectInfo from '@/views/teacher/Project/ProjectInfo'
import TeacherEvaluateManageInfo from '@/views/teacher/Project/EvaluateManageInfo'
import TeacherProjectMemberInfo from '@/views/teacher/Project/ProjectMemberInfo'
import TeacherMaterialUpload from '@/views/teacher/Project/MaterialUpload'
import TeacherCheckStu from '@/views/teacher/User/StudentInfo'

// 学生详情页面
import StudentSignUpInfo from '@/views/student/ProjectCheck/SignUpInfo'
import StudentMidManageInfo from '@/views/student/ProjectCheck/MidManageInfo'
import StudentEndManageInfo from '@/views/student/ProjectCheck/EndManageInfo'
import StudentProjectInfo from '@/views/student/ProjectCheck/ProjectInfo'
import StudentEvaluateManageInfo from '@/views/student/ProjectCheck/EvaluateManageInfo'
import StudentProjectManage from '@/views/student/ProjectManage/ProjectInfo'
import StudentProjectMemberInfo from '@/views/student/ProjectManage/ProjectMemberInfo'
import StudentMaterialUpload from '@/views/student/ProjectManage/MaterialUpload'
import StudentAudit from '@/views/student/Audit/StudentAudit'
import StudentAuditAdd from '@/views/student/Audit/StudentAuditAdd'

// 评审详情页面
import ReviewManageInfo from '@/views/review/ReviewManageInfo'

Vue.use(Router)

export default new Router({
  routes: [
    {
      path: '/',
      name: 'Index',
      component: Index
    },
    {
      path: '/index',
      name: 'Index',
      component: Index
    },
    {
      path: '/login',
      name: 'Login',
      component: Login
    },
    {
      path: '/register',
      name: 'Register',
      component: Login
    },
    {
      // 这个id是动态生成的
      path: '/FileShow/:id',
      name: 'FileShow',
      component: FileShow
    },
    {
      'path': '/admin',
      component: Admin,
      meta: {requiresAuth: true},
      redirect: '/admin/Notification',
      children: [
        // 通知管理
        {
          name: 'Notification',
          path: 'Notification',
          component: Notification
        },
        // 项目报名管理
        {
          name: 'ProjectSignUp',
          path: 'ProjectSignUp',
          component: ProjectSignUp
        },
        // 项目成员审核
        {
          name: 'ProjectAudit',
          path: 'ProjectAudit',
          component: ProjectAudit
        },
        // 项目中期管理
        {
          name: 'ProjectMidManage',
          path: 'ProjectMidManage',
          component: ProjectMidManage
        },
        // 项目结题管理
        {
          name: 'ProjectEndManage',
          path: 'ProjectEndManage',
          component: ProjectEndManage
        },
        // 项目成员管理
        {
          name: 'ProjectMemberManage',
          path: 'ProjectMemberManage',
          component: ProjectMemberManage
        },
        // 项目信息管理
        {
          name: 'ProjectInfoManage',
          path: 'ProjectInfoManage',
          component: ProjectInfoManage
        },
        // 项目评审管理
        {
          name: 'ProjectEvaluateManage',
          path: 'ProjectEvaluateManage',
          component: ProjectEvaluateManage
        },
        // 评审管理
        {
          name: 'EvaluationExperts',
          path: 'EvaluationExperts',
          component: EvaluationExperts
        },
        // 用户基本信息
        {
          name: 'UserInfoManage',
          path: 'UserInfoManage',
          component: UserInfoManage
        },
        // 教师信息管理
        {
          name: 'TeacherInfoManage',
          path: 'TeacherInfoManage',
          component: TeacherInfoManage
        },
        // 学生信息管理
        {
          name: 'StudentInfoManage',
          path: 'StudentInfoManage',
          component: StudentInfoManage
        },
        // 评审专家信息管理
        {
          name: 'OtherInfoManage',
          path: 'OtherInfoManage',
          component: OtherInfoManage
        }
      ]
    },
    {
      'path': '/teacher',
      component: Teacher,
      meta: {requiresAuth: true},
      redirect: '/teacher/TeacherMyInfo',
      children: [
        // 个人中心
        {
          name: 'TeacherMyInfo',
          path: 'TeacherMyInfo',
          component: TeacherMyInfo
        },
        // 项目报名查看
        {
          name: 'TeacherProjectSignUp',
          path: 'TeacherProjectSignUp',
          component: TeacherProjectSignUp
        },
        // 项目中期查看
        {
          name: 'TeacherProjectMidManage',
          path: 'TeacherProjectMidManage',
          component: TeacherProjectMidManage
        },
        // 项目结题查看
        {
          name: 'TeacherProjectEndManage',
          path: 'TeacherProjectEndManage',
          component: TeacherProjectEndManage
        },
        // 项目成员查看
        {
          name: 'TeacherProjectMemberManage',
          path: 'TeacherProjectMemberManage',
          component: TeacherProjectMemberManage
        },
        // 项目信息查看
        {
          name: 'TeacherProjectInfoManage',
          path: 'TeacherProjectInfoManage',
          component: TeacherProjectInfoManage
        },
        // 项目评审查看
        {
          name: 'TeacherProjectEvaluateManage',
          path: 'TeacherProjectEvaluateManage',
          component: TeacherProjectEvaluateManage
        },
        // 项目评审查看
        {
          name: 'TeacherProjectMaterialManage',
          path: 'TeacherProjectMaterialManage',
          component: TeacherProjectMaterialManage
        }
      ]
    },
    {
      'path': '/student',
      component: Student,
      meta: {requiresAuth: true},
      redirect: '/student/StudentMyInfo',
      children: [
        // 个人中心
        {
          name: 'StudentMyInfo',
          path: 'StudentMyInfo',
          component: StudentMyInfo
        },
        // 项目报名查看
        {
          name: 'StudentProjectSignUp',
          path: 'StudentProjectSignUp',
          component: StudentProjectSignUp
        },
        // 项目中期查看
        {
          name: 'StudentProjectEndCheck',
          path: 'StudentProjectEndCheck',
          component: StudentProjectEndCheck
        },
        // 项目结题查看
        {
          name: 'StudentProjectMidCheck',
          path: 'StudentProjectMidCheck',
          component: StudentProjectMidCheck
        },
        // 项目信息查看
        {
          name: 'StudentProjectCheck',
          path: 'StudentProjectCheck',
          component: StudentProjectCheck
        },
        // 项目评审查看
        {
          name: 'StudentProjectEvaluateCheck',
          path: 'StudentProjectEvaluateCheck',
          component: StudentProjectEvaluateCheck
        },
        {
          name: 'StudentProjectInfoManage',
          path: 'StudentProjectInfoManage',
          component: StudentProjectInfoManage
        },
        {
          name: 'StudentProjectMaterialManage',
          path: 'StudentProjectMaterialManage',
          component: StudentProjectMaterialManage
        },
        {
          name: 'StudentProjectMemberManage',
          path: 'StudentProjectMemberManage',
          component: StudentProjectMemberManage
        },
        {
          name: 'AuditManage',
          path: 'AuditManage',
          component: AuditManage
        }
      ]
    },
    {
      'path': '/review',
      component: Review,
      meta: {requiresAuth: true},
      redirect: '/review/ReviewMyInfo',
      children: [
        // 个人中心
        {
          name: 'ReviewMyInfo',
          path: 'ReviewMyInfo',
          component: ReviewMyInfo
        },
        {
          name: 'ReviewTask',
          path: 'ReviewTask',
          component: ReviewTask
        }
      ]
    },
    // 个人中心修改
    {
      path: '/ChangeInfo/:userId',
      name: 'ChangeInfo',
      component: ChangeInfo
    },
    // 管理员端

    // 通知修改，查询页面
    {
      path: '/Notifyinfo/:type/:notId',
      name: 'Notifyinfo',
      component: Notifyinfo
    },
    // 通知添加页面
    {
      path: '/NotifyinfoAdd',
      name: 'NotifyinfoAdd',
      component: NotifyinfoAdd
    },
    // 项目修改，查询页面
    // 项目结题
    {
      path: '/EndManageInfo/:type/:proId',
      name: 'EndManageInfo',
      component: EndManageInfo
    },
    // 项目中期
    {
      path: '/MidManageInfo/:type/:proId',
      name: 'MidManageInfo',
      component: MidManageInfo
    },
    // 项目评审
    {
      path: '/EvaluateManageInfo/:type/:proId',
      name: 'EvaluateManageInfo',
      component: EvaluateManageInfo
    },
    // 项目信息
    {
      path: '/ProjectInfo/:type/:proId',
      name: 'ProjectInfo',
      component: ProjectInfo
    },
    // 项目申请
    {
      path: '/SignUpInfo/:type/:proId',
      name: 'SignUpInfo',
      component: SignUpInfo
    },
    // 项目添加页面
    {
      path: '/SignUpInfoAdd',
      name: 'SignUpInfoAdd',
      component: SignUpInfoAdd
    },
    // 项目审核
    {
      path: '/ProjectAuditInfo/:recId',
      name: 'ProjectAuditInfo',
      component: ProjectAuditInfo
    },
    // 项目成员管理
    {
      path: '/ProjectMemberInfo/:proId/:userId',
      name: 'ProjectMemberInfo',
      component: ProjectMemberInfo
    },

    // 用户管理
    // 基本用户信息
    {
      path: '/UserInfo/:type/:userId',
      name: 'UserInfo',
      component: UserInfo
    },
    // 教师信息
    {
      path: '/TeacherInfo/:type/:userId',
      name: 'TeacherInfo',
      component: TeacherInfo
    },
    // 学生信息
    {
      path: '/StudentInfo/:type/:userId',
      name: 'StudentInfo',
      component: StudentInfo
    },
    // 评审专家信息
    {
      path: '/OtherInfo/:type/:userId',
      name: 'OtherInfo',
      component: OtherInfo
    },

    // 教师端
    {
      path: '/TeacherSignUpInfo/:proId',
      name: 'TeacherSignUpInfo',
      component: TeacherSignUpInfo
    },
    {
      path: '/TeacherSignUpInfoAdd',
      name: 'TeacherSignUpInfoAdd',
      component: TeacherSignUpInfoAdd
    },
    {
      path: '/TeacherMidManageInfo/:proId',
      name: 'TeacherMidManageInfo',
      component: TeacherMidManageInfo
    },
    {
      path: '/TeacherEndManageInfo/:proId',
      name: 'TeacherEndManageInfo',
      component: TeacherEndManageInfo
    },
    {
      path: '/TeacherProjectInfo/:type/:proId/',
      name: 'TeacherProjectInfo',
      component: TeacherProjectInfo
    },
    {
      path: '/TeacherEvaluateManageInfo/:proId',
      name: 'TeacherEvaluateManageInfo',
      component: TeacherEvaluateManageInfo
    },
    {
      path: '/TeacherProjectMemberInfo/:proId/:userId',
      name: 'TeacherProjectMemberInfo',
      component: TeacherProjectMemberInfo
    },
    {
      path: '/TeacherMaterialUpload/:proId/:proName',
      name: 'TeacherMaterialUpload',
      component: TeacherMaterialUpload
    },
    {
      path: '/TeacherCheckStu/:userId',
      name: 'TeacherCheckStu',
      component: TeacherCheckStu
    },
    // 学生端
    {
      path: '/StudentSignUpInfo/:proId',
      name: 'StudentSignUpInfo',
      component: StudentSignUpInfo
    },
    {
      path: '/StudentMidManageInfo/:proId',
      name: 'StudentMidManageInfo',
      component: StudentMidManageInfo
    },
    {
      path: '/StudentEndManageInfo/:proId',
      name: 'StudentEndManageInfo',
      component: StudentEndManageInfo
    },
    {
      path: '/StudentProjectInfo/:proId',
      name: 'StudentProjectInfo',
      component: StudentProjectInfo
    },
    {
      path: '/StudentEvaluateManageInfo/:proId',
      name: 'StudentEvaluateManageInfo',
      component: StudentEvaluateManageInfo
    },
    {
      path: '/StudentProjectManage/:type/:proId',
      name: 'StudentProjectManage',
      component: StudentProjectManage
    },
    {
      path: '/StudentProjectMemberInfo/:proId/:userId',
      name: 'StudentProjectMemberInfo',
      component: StudentProjectMemberInfo
    },
    {
      path: '/StudentMaterialUpload',
      name: 'StudentMaterialUpload',
      component: StudentMaterialUpload
    },
    {
      path: '/StudentAudit/:userId/:proId',
      name: 'StudentAudit',
      component: StudentAudit
    },
    {
      path: '/StudentAuditAdd',
      name: 'StudentAuditAdd',
      component: StudentAuditAdd
    },
    // 评审
    {
      path: '/ReviewManageInfo/:type/:proId',
      name: 'ReviewManageInfo',
      component: ReviewManageInfo
    }
  ]
})
