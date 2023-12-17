<template>
    <div id="m-top_index" style="min-height: 65vh">
        <img src="@/assets/img/lufei.jpg" alt="" class="ui image m-top-img">
        <div class="m-class_coverIndex m-center" >
            <div class="m-margin-top-large" align="center">
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
            <div class="m-font-size-text m-gradient-color m-margin-top-large">
                须知少日拏云志 <br>
                曾许人间第一流
            </div>

            <div class="ui container m-margin-top-big" style="margin-bottom: 0; padding-bottom: 0">
                <a href="#main-index" class="ui circular icon button m-opacity-small"><i class="home icon"></i> 首页</a>
                <router-link :to = "name = 'type'" class="ui circular icon button m-opacity-small"><i class="clone outline icon"></i> 分类</router-link>
                <router-link :to = "name = 'archives'" class="ui circular icon button m-opacity-small"><i class="clock outline  icon"></i> 时间轴</router-link>
                <router-link :to = "name = 'about'" class="ui circular icon button m-opacity-small"><i class="info icon"></i> 关于我</router-link>
            </div>
            <div id="waypoint" style="margin-top: 0;padding-top: 0"></div>
        </div>
    </div>
    <div  id="main-index"></div>

    <ContentFieldCom>
        <div class="ui middle aligned container stacked grid">
            <div class="ui vertical segment">
                <!--博客-->
                <div class="ui padded segment m-opacity">

                    <div class="ui mobile reversed stackable grid  m-margin-top-large">
                        <!--博文信息-->
                        <div class="eleven wide column">
                            <h3 class="ui header" >
                                <a href="#" target="_blank" class="m-black m-title-font" >大圣，此去欲何?</a>
                                <div class="ui mini blue basic button" style="margin-left: 10px" >置顶</div>
                            </h3>
                            <p class="m-text m-margin-top-max" style="min-width: 50vw;" >戴上金箍，没法爱你；放下金箍，没法保护你。我知道上天不会给我第二次机会，曾经我们说好的永远，原来也仅仅只有，十二画，而已。“大圣，此去欲何?”“踏南天，碎凌霄。”“若一去不回……”“便一去不回” 其实很多时候，我们都是有机会的，最后真正放弃的，是我们自己。......</p>
                            <div class="ui m-margin-top-max stackable grid">
                                <div class="eleven wide column">
                                    <div class="ui mini horizontal link list">
                                        <div class="item">
                                            <img src="@/assets/img/xiu.jpg" alt="" class="ui avatar image">
                                            <div class="content"><a href="#" class="header" >xiu du</a></div>
                                        </div>
                                        <div class="item">
                                            <i class="calendar icon"></i><span>2023-05-26</span>
                                        </div>
                                        <div class="item">
                                            <i class="eye icon"></i> <span>2222</span>
                                        </div>
                                        <!--                                        <div class="item">-->
                                        <!--                                            <i class="comment outline icon"></i> <span>2222</span>-->
                                        <!--                                        </div>-->
                                    </div>
                                </div>
                                <div class="right aligned five wide column">
                                    <a href="#" target="_blank" class="ui teal basic label m-padded-tiny m-text-thin">好文</a>
                                </div>
                            </div>
                        </div>
                        <!--博文图片-->
                        <div class="five wide column">
                            <a href="#" target="_blank">
                                <img src="@/assets/img/Hancock.jpg" alt="" class="ui rounded image" style="width: 400px; height: 200px">
                            </a>
                        </div>

                    </div>
                </div>
                <!--博客-->
                <div class="ui padded segment m-opacity">

                    <div class="ui mobile reversed stackable grid  m-margin-top-large">
                        <!--博文信息-->
                        <div class="eleven wide column">
                            <h3 class="ui header" >
                                <a href="#" target="_blank" class="m-black m-title-font" >大圣，此去欲何?</a>
                                <div class="ui mini blue basic button" style="margin-left: 10px" >置顶</div>
                            </h3>
                            <p class="m-text m-margin-top-max" style="min-width: 50vw;" >戴上金箍，没法爱你；放下金箍，没法保护你。我知道上天不会给我第二次机会，曾经我们说好的永远，原来也仅仅只有，十二画，而已。“大圣，此去欲何?”“踏南天，碎凌霄。”“若一去不回……”“便一去不回” 其实很多时候，我们都是有机会的，最后真正放弃的，是我们自己。......</p>
                            <div class="ui m-margin-top-max stackable grid">
                                <div class="eleven wide column">
                                    <div class="ui mini horizontal link list">
                                        <div class="item">
                                            <img src="@/assets/img/xiu.jpg" alt="" class="ui avatar image">
                                            <div class="content"><a href="#" class="header" >xiu du</a></div>
                                        </div>
                                        <div class="item">
                                            <i class="calendar icon"></i><span>2023-05-26</span>
                                        </div>
                                        <div class="item">
                                            <i class="eye icon"></i> <span>2222</span>
                                        </div>
                                        <!--                                        <div class="item">-->
                                        <!--                                            <i class="comment outline icon"></i> <span>2222</span>-->
                                        <!--                                        </div>-->
                                    </div>
                                </div>
                                <div class="right aligned five wide column">
                                    <a href="#" target="_blank" class="ui teal basic label m-padded-tiny m-text-thin">好文</a>
                                </div>
                            </div>
                        </div>
                        <!--博文图片-->
                        <div class="five wide column">
                            <a href="#" target="_blank">
                                <img src="@/assets/img/Hancock.jpg" alt="" class="ui rounded image" style="width: 400px; height: 200px">
                            </a>
                        </div>

                    </div>
                </div>

                <!-- 分页-->
                <div class="ui bottom segment m-opacity stackable grid">
                    <div class="three wide column center aligned">
                        <a class="ui teal basic button item">上一页</a>
                    </div>

                    <div class="ten wide column center aligned" style="margin: auto">
                        <p> <span> 1 </span> / <span> 1 </span> </p>
                    </div>
                    <div class="three wide column center aligned">
                        <a class="ui teal basic button item">下一页</a>
                    </div>
                </div>
            </div>

        </div>
    </ContentFieldCom>
</template>

<script>
import ContentFieldCom from "@/components/ContentFieldCom";
export default {
    name: "IndexView",
    components: {ContentFieldCom},
    setup() {

    }
}
</script>

<style scoped>

</style>