<template>

    <div class="my-login" style="max-width: 30em;">
        <div class="ui container">

            <div class="ui middle aligned center aligned grid">
                <div class="column">
                    <h2 class="ui black image header">
                        后台登录
                    </h2>
                    <form class="ui large form" method="post" action="#">
                        <div class="ui segment">

                            <div class="field">
                                <div class="ui left icon input">
                                    <i class="user icon"></i>
                                    <input type="text" name="username" placeholder="用户名">
                                </div>
                            </div>

                            <div class="field">
                                <div class="ui left icon input">
                                    <i class="lock icon"></i>
                                    <input type="password" name="password" placeholder="密码">
                                </div>
                            </div>
                            <button class="ui fluid large teal submit button">登   录</button>
                        </div>

                        <div v-if="message" class="ui mini negative message"> {{message}} </div>
                    </form>

                </div>
            </div>

        </div>
    </div>

</template>

<script>
import {ref} from "vue";
export default {
    name: "UserLoginView",
    setup() {
        let message = ref("");

        return {
            message
        }
    }
}
</script>

<style scoped>
.my-login {
    max-width: 60em;
    margin: 5em auto auto;
    padding-top: 3em;
    padding-bottom: 2em;
}

</style>