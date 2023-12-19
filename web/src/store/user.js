import axios from "axios";
import router from "@/router";


export default({
    state: {
        is_login: false,
        id: 0,
        nickname: "",
        username: "",
        avatar: "",
        token: "",
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
        },
        updateLoading(state, loading) {
            state.loading = loading;
        },
        updateToken(state, token) {
            state.token = token;
        }
    },
    actions: {
        userLogin(content, data) {
            const param = {
                username: data.username,
                password: data.password,
            }
            axios.post("http://127.0.0.1:3000/user/login", param, {
                headers: {
                    'Content-Type': 'application/json',
                },
            })
            .then(resp => {
                if(resp.data.error === "success") {
                    data.success();
                    localStorage.setItem("token", resp.data.data.token);
                    content.commit("updateToken", resp.data.data.token);
                    router.push({name: "user_index"});
                }
                else data.error(resp.data);
            })
            .catch(error => {
                data.error(error);
            });
        },
        userUpdateInfo(content, data) {
            axios.get("http://127.0.0.1:3000/user/check", {
                headers: {
                    "satoken": data.token,
                    'Content-Type': "application/json",
                },
            })
            .then(resp => {
                if(resp.data.error === "success") {
                    content.commit("updateUser", {
                        ...resp.data.data,
                        is_login: true,
                    })
                    content.commit("updateToken", resp.data.data.token);
                    data.success(resp.data);
                }else data.error(resp);
            })
            .catch(error => {
                data.error(error);
            })
        },
        userLogout(content, data) {
            axios.get("http://127.0.0.1:3000/user/logout", {
                headers: {
                    "satoken": data.token,
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
