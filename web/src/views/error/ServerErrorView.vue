<template>
    <ContentFieldCom>
        <div style="opacity: 0.8">
            <img src="@/assets/img/effect.gif" alt="" class="ui rounded bordered fluid image">
        </div>
        <div class="m-margin-top-max" align="center">
            <a href="/index">
                <button type="button" class="ui teal button m-mobile-wide"><i class="home icon"></i> 返回首页</button>
            </a>
        </div>
    </ContentFieldCom>
</template>

<script>
import ContentFieldCom from "@/components/ContentFieldCom";
export default {
    name: "NotFoundView",
    components: {ContentFieldCom}
}
</script>

<style scoped>

</style>