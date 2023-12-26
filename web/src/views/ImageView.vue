<template>
    <div id="m-top_index" style="min-height: 65vh">
        <img src="@/assets/img/lufei.jpg" alt="" class="ui image m-top-img">
        <div class="m-class_coverIndex m-center" >
            <div style="margin-top: 7em" align="center">
                <img src="@/assets/img/xiu.jpg" alt="" class="ui circular small image">
            </div>
            <div class="m-font-size-title" style="color: black">锈渎</div>
            <div>
                <div class="ui circular icon button"><i class="qq icon"></i></div>
                <a href="https://github.com/xiudu123" target="_blank" class="ui circular icon button"><i class="github icon"></i></a>
            </div>
            <div class="My_qq_qr ui flowing popup top left transition hidden">
                <img src="@/assets/img/qrcode.png" alt="" class="ui rounded image">
            </div>
            <div class="m-font-size-text m-gradient-color m-margin-top-large" style="font-family: myFont,serif;">
                须知少日拏云志 <br>
                曾许人间第一流
            </div>

            <div class="ui container m-margin-top-big" style="margin-bottom: 0; padding-bottom: 0">
                <router-link :to = "name = 'index'" class="ui circular icon button m-opacity-small"><i class="home icon"></i> 首页</router-link>
                <router-link :to = "name = 'type'" class="ui circular icon button m-opacity-small"><i class="clone outline icon"></i> 分类</router-link>
                <router-link :to = "name = 'archives'" class="ui circular icon button m-opacity-small"><i class="clock outline  icon"></i> 时间轴</router-link>
                <router-link :to = "name = 'about'" class="ui circular icon button m-opacity-small"><i class="info icon"></i> 关于我</router-link>
            </div>
            <div id="waypoint" style="margin-top: 0;padding-top: 0"></div>
        </div>
    </div>
</template>

<script>
export default {
    name: "ImageView"
}
</script>

<style scoped>

</style>