<template>
    <div id="toolbar" class="m-padded-tb-large m-fixed m-right-bottom toTop-button" @click="click_toolbar">
        <div class="ui teal icon button"><i class="chevron up icon"></i> </div>
    </div>
</template>

<script>
export default {
    name: "ToolbarCom",
    setup() {
        const click_toolbar = () => {
            // eslint-disable-next-line no-undef
            $(window).scrollTo(0, 500);
        }
        return {
            click_toolbar,
        }
    }
}
</script>

<style scoped>

</style>