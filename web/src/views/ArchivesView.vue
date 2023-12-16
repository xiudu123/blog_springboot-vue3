<template>
<ContentFieldCom>

    <div class="ui segment center aligned m-opacity">
        共 <h2 class="ui orange header m-text-thin m-inline-block">200</h2> 篇博客
    </div>

    <div class="ui big fluid vertical menu m-opacity" >
        <h2 class="ui header m-margin-tb-tiny">
            <div  align="center">2023</div>
            <div style="text-align: right">
                共 <span style="color: orange">123</span> 篇
            </div>
        </h2>

        <a href="#" target="_blank" class="item m-padded">
                <span>
                    <i class="mini teal circle icon"></i>&nbsp;&nbsp; <span >这是一篇博客</span>
                </span>
            <div class="ui mini blue basic button" style="margin-left: 10px" >置顶</div>
            <div class="ui teal basic left pointing label m-padded-tb-tiny">
                <span >5</span>
                月
                <span >27</span>
            </div>
        </a>
    </div>

</ContentFieldCom>
</template>

<script>
import ContentFieldCom from "@/components/ContentFieldCom";
export default {
    name: "ArchivesView",
    components: {ContentFieldCom},
}

</script>

<style scoped>

</style>