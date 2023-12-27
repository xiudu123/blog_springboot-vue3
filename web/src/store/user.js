import axios from "axios";
import router from "@/router";


export default({
    state: {
        is_login: false,
        id: 0,
        nickname: "",
        username: "",
        avatar: "",
        email: "",
        loading: false, // 数据加载状态
    },
    getters: {

    },
    mutations: {
        updateUser(state, user) {
            state.id = user.id;
            state.username = user.username;
            state.nickname = user.nickname;
            state.avatar = user.avatar;
            state.is_login = user.is_login;
            state.email = user.email;
        },
        updateLoading(state, loading) {
            state.loading = loading;
        },
    },
    actions: {
        userLogin(content, data) {
            const param = {
                username: data.username,
                password: data.password,
            }
            axios.post(process.env.VUE_APP_API_BASE_URL + "/user/login", param, {
                headers: {
                    'Content-Type': 'application/json',
                },
            })
            .then(resp => {
                if(resp.data.error === "success") {
                    data.success();
                    localStorage.setItem("token", resp.data.data.token);
                    router.push({name: "user_index"});
                }
                else data.error(resp.data);
            })
            .catch(error => {
                data.error(error);
            });
        },
        userUpdateInfo(content, data) {
            axios.get(process.env.VUE_APP_API_BASE_URL + "/user/check", {
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': "application/json",
                },
            })
            .then(resp => {
                if(resp.data.error === "success") {
                    content.commit("updateUser", {
                        id: resp.data.data.id,
                        username: resp.data.data.username,
                        nickname: resp.data.data.nickname,
                        avatar: resp.data.data.avatar,
                        email: resp.data.data.email,
                        is_login: true,
                    })
                    data.success(resp.data);
                }else data.error(resp);
            })
            .catch(error => {
                data.error(error);
            })
        },
        userLogout() {
            axios.get(process.env.VUE_APP_API_BASE_URL + "/user/logout", {
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': "application/json",
                },
            }).then(() => {
                localStorage.removeItem("token");
                router.push({name: "user_login"});
            }).catch(() => {

            })
        }


    },
    modules: {
    }
})
