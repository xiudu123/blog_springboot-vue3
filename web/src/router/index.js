import { createRouter, createWebHistory } from 'vue-router'
import ApiView from '../views/ApiView'
import AboutView from "@/views/AboutView";
import ArchivesView from "@/views/ArchivesView";
import IndexView from "@/views/IndexView";
import TypeView from "@/views/TypeView";
import MessageView from "@/views/MessageView";
import NotFoundView from "@/views/NotFoundView";
import UserIndexView from "@/views/user/UserIndexView";
import UserBlogInputView from "@/views/user/UserBlogInputView";
import UserBlogManageView from "@/views/user/UserBlogManageView";
import UserLoginView from "@/views/user/UserLoginView";
import UserTypeInput from "@/views/user/UserTypeInputView";
import UserTypeManage from "@/views/user/UserTypeManageView";
import BlogView from "@/views/BlogView";
import {nextTick} from "vue";
const routes = [
  {
    path: "/index",
    name: "index",
    component: IndexView
  },
  {
    path: "/about",
    name: "about",
    component: AboutView
  },
  {
    path: "/archives",
    name: "archives",
    component: ArchivesView
  },
  {
    path: "/type",
    name: "type",
    component: TypeView
  },
  {
    path: "/message",
    name: "message",
    component: MessageView
  },
  {
    path: "/blog",
    name: "blog",
    component: BlogView
  },
  {
    path: "/user/login",
    name: "user_login",
    component: UserLoginView
  },
  {
    path: "/user/index",
    name: "user_index",
    component: UserIndexView
  },
  {
    path: "/user/blog/input",
    name: "user_blog_input",
    component: UserBlogInputView
  },
  {
    path: "/user/blog/manage",
    name: "user_blog_manage",
    component: UserBlogManageView
  },
  {
    path: "/user/type/input",
    name: "user_type_input",
    component: UserTypeInput
  },
  {
    path: "/user/type/manage",
    name: "user_type_manage",
    component: UserTypeManage
  },
  {
    path: "/api",
    name: 'api',
    component: ApiView
  },
  {
    path: "/404/",
    name: "404",
    component: NotFoundView
  },
  {
    path: "/:catAll(.*)",
    redirect: "/404/",
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// 导航完成后滚动到顶部
router.afterEach(() => {
  // 确保在页面渲染完毕后执行滚动
  nextTick(() => {
    // eslint-disable-next-line no-undef
    $(window).scrollTo(0, 500);
  });
});


export default router
