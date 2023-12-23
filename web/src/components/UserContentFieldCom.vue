<template>
    <!--导航-->
    <UserNavbarCom/>
    <!--中间元素-->
    <div id="main">
        <div class="ui container">
            <slot></slot>
        </div>
    </div>

    <ToolbarCom/>
    <!--底部footer-->
<FooterCom/>
</template>

<script>
import UserNavbarCom from "@/components/UserNavbarCom";
import FooterCom from "@/components/FooterCom";
import ToolbarCom from "@/components/ToolbarCom";
export default {
    name: "UserContentFieldCom",
    components: {ToolbarCom, FooterCom, UserNavbarCom}
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