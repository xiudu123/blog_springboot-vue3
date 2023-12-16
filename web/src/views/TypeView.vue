<template>
    <ContentFieldCom>
        <div class="ui segment m-padded-tb-large m-opacity">
            <div class="ui container center aligned">
                <div class="ui labeled button m-margin-tb-tiny">
                    <a href="#" class="ui basic button" >只</a>
                    <div class="ui basic  left pointing label">24</div>
                </div>
            </div>
        </div>

        <div class="ui top attached green segment m-opacity ">
            <!--博客-->
            <div class="ui padded segment m-opacity">

                <div class="ui mobile reversed stackable grid  m-margin-top-large" style="min-width: 50vw;">
                    <!--博文信息-->
                    <div class="eleven wide column left floated">
                        <h3 class="ui header" >
                            <a href="#" target="_blank" class="m-black m-title-font">大圣，此去欲何?</a>
                            <div class="ui mini blue basic button" style="margin-left: 10px">置顶</div>
                        </h3>
                        <p class="m-text m-margin-top-max" >戴上金箍，没法爱你；放下金箍，没法保护你。我知道上天不会给我第二次机会，曾经我们说好的永远，原来也仅仅只有，十二画，而已。“大圣，此去欲何?”“踏南天，碎凌霄。”“若一去不回……”“便一去不回” 其实很多时候，我们都是有机会的，最后真正放弃的，是我们自己。......</p>
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
                            <img src="@/assets/img/Hancock.jpg" alt="" class="ui rounded image" style="height: 200px">
                        </a>
                    </div>

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
    </ContentFieldCom>
</template>

<script>
import ContentFieldCom from "@/components/ContentFieldCom";
export default {
    name: "TypeView",
    components: {ContentFieldCom}
}
</script>

<style scoped>

</style>