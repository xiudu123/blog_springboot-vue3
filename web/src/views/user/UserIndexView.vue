<template>
<UserContentFieldCom>
    <div class="ui success large message">
        <h2>登录成功</h2>
    </div>
    <img src="@/assets/img/Ace2.jpg" alt="" class="ui rounded bordered fluid image" style="height: 400px">
</UserContentFieldCom>
</template>

<script>
import UserContentFieldCom from "@/components/UserContentFieldCom";
export default {
    name: "UserIndex",
    components: {UserContentFieldCom},
    setup() {
        document.title = "登录成功";
    }
}
</script>

<style scoped>

</style>