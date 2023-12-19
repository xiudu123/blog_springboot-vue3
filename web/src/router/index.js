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
import store from "@/store";
const routes = [
  {
    path: "/",
    name: "home",
    redirect: "/index",
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/index",
    name: "index",
    component: IndexView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/about",
    name: "about",
    component: AboutView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/archives",
    name: "archives",
    component: ArchivesView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/type",
    name: "type",
    component: TypeView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/message",
    name: "message",
    component: MessageView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/blog",
    name: "blog",
    component: BlogView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/user/login",
    name: "user_login",
    component: UserLoginView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/user/index",
    name: "user_index",
    component: UserIndexView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/user/blog/input",
    name: "user_blog_input",
    component: UserBlogInputView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/user/blog/manage",
    name: "user_blog_manage",
    component: UserBlogManageView,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/user/type/input",
    name: "user_type_input",
    component: UserTypeInput,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/user/type/manage",
    name: "user_type_manage",
    component: UserTypeManage,
    meta: {
      requestAuth: true,
    }
  },
  {
    path: "/api",
    name: 'api',
    component: ApiView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/404/",
    name: "404",
    component: NotFoundView,
    meta: {
      requestAuth: false,
    }
  },
  {
    path: "/:catAll(.*)",
    redirect: "/404/",
    meta: {
        requestAuth: false,
    }
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

// 处理页面访问权限问题
router.beforeEach((to, from, next) => {
  if(!store.state.user.is_login && localStorage.getItem("token")) {
      store.dispatch("userUpdateInfo", {
        token: localStorage.getItem("token"),
        success() {

        },
        error() {
          localStorage.removeItem("token");
        },
      }).then(() => {

      }).catch(() => {

      });
    next();
  }else if(to.meta.requestAuth && !store.state.user.is_login) {
    next({name: "user_login"});
  }else {
    next();
  }
  store.commit("updateLoading", true);
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
