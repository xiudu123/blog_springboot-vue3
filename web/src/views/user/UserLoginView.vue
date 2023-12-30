<template>

    <div class="my-login">
        <div class="ui container">

            <div class="ui middle aligned center aligned grid">
                <div class="column">
                    <h2 class="ui black image header">
                        后台登录
                    </h2>
                    <form class="ui large form" method="post" @submit.prevent="submitLogin">
                        <div class="ui segment">

                            <div class="field">
                                <div class="ui left icon input">
                                    <i class="user icon"></i>
                                    <input type="text" v-model="username" placeholder="用户名">
                                </div>
                            </div>

                            <div class="field">
                                <div class="ui left icon input">
                                    <i class="lock icon"></i>
                                    <input type="password" v-model="password" placeholder="密码">
                                </div>
                            </div>
                            <button class="ui fluid large teal submit button">登   录</button>
                        </div>

                    </form>

                    <div v-if="error_message" class="ui mini negative message"> {{error_message}} </div>
                </div>
            </div>

        </div>
    </div>


</template>

<script>
import {onMounted, ref} from "vue";
import store from "@/store";
export default {
    name: "UserLoginView",
    setup() {
        let error_message = ref("");
        let username = ref(""), password = ref("");
        onMounted(() => {
            document.title = "登录界面";
        })

        const submitLogin = () => {
            error_message.value = "";

            store.dispatch("userLogin", {
                username: username.value,
                password: password.value,
                success() {
                },
                error(error) {
                    error_message.value = error.error;
                }
            })

        };

        return {
            error_message,
            username,
            password,
            submitLogin
        }
    }
}
</script>

<style scoped>
.my-login {
    max-width: 30em;

    /*页面居中*/
    position: absolute;
    top: 50%;
    left: 50%;
    transform: translate(-50%, -50%);
}

</style>