<template>
    <NavbarCom/>

    <router-view/>

    <ToolbarCom/>
    <FooterCom/>
</template>

<script>
import NavbarCom from "@/components/NavbarCom";
import FooterCom from "@/components/FooterCom";
import ToolbarCom from "@/components/ToolbarCom";
export default {
    components: {ToolbarCom, FooterCom, NavbarCom}
}

</script>

<style>
body{
    background-image: url("@/assets/img/background3.jpg");
    background-attachment:fixed;
    background-size: 100%;
    no-repeat: center top;
    font: 14px/1.5 Tahoma,Helvetica,Arial,'宋体',sans-serif;
    height: 100%;
    width: 100%;
}

</style>
