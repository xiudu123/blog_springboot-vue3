import { createStore } from 'vuex'
import ModuleUser from './user'
export default createStore({
  state: {// 应用的状态数据
  },
  getters: {// 在这里定义从状态派生出的一些计算属性
  },
  mutations: {// 在这里定义同步修改 state 的方法
  },
  actions: {// 在这里定义异步修改 state 的方法
  },
  modules: {// 在这里定义模块
    user: ModuleUser
  }
})
