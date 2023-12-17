<template>
    <!--导航-->
<User/>
    <UserNavbarCom/>
    <!--中间元素-->
    <div id="main">
        <div class="ui container">
            <slot></slot>
        </div>
    </div>

    <!--底部footer-->
<FooterCom/>
</template>

<script>
import UserNavbarCom from "@/components/UserNavbarCom";
import FooterCom from "@/components/FooterCom";
export default {
    name: "UserContentFieldCom",
    components: {FooterCom, UserNavbarCom}
}
</script>

<style scoped>
#main {
    min-height: 70vh;
    margin-top: 5em;
    padding-top: 3em;
    padding-bottom: 2em;
}
</style>