import { createRouter, createWebHistory } from 'vue-router'
import ApiView from '../views/ApiView'
import AboutView from "@/views/AboutView";
import ArchivesView from "@/views/ArchivesView";
import IndexView from "@/views/IndexView";
import TypeView from "@/views/TypeView";
import MessageView from "@/views/MessageView";
import NotFoundView from "@/views/NotFoundView";
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

export default router
