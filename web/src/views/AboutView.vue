<template>

<ContentFieldCom>

    <div class="ui segment m-opacity m-center">
        <div class="m-margin-top-large" align="center">
            <img src="@/assets/img/xiu.jpg" alt="" class="ui circular small image">
        </div>
        <div class="my-font">锈渎</div>
        <div>
            <a href="https://github.com/xiudu123" target="_blank" class="ui circular icon button"><i class="github icon"></i></a>
        </div>

        <hr style="width: 30%">
        <br>
        <div style="min-height: 300px;">
            <p>一位对编程感兴趣的菜狗 acmer</p>
        </div>
    </div>

</ContentFieldCom>
</template>

<script>
import ContentFieldCom from "@/components/ContentFieldCom";
import {onMounted} from "vue";
export default {
    name: "AboutView",
    components: {ContentFieldCom},
    setup() {
        onMounted(() => {
            document.title = "关于我";
        })
    }
}
</script>

<style scoped>
.my-font {
    font-size: 30px;
    color: black;
    font-family: STSong, serif;

}
</style>