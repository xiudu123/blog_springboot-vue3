<template>
    <div v-if="route_name !== 'blog'" id="toolbar" class="m-padded-tb-large m-fixed m-right-bottom toTop-button" @click="click_toolbar">
        <div class="ui teal icon button"><i class="chevron up icon"></i> </div>
    </div>
</template>

<script>
import {useRoute} from "vue-router";
import {computed} from "vue";

export default {
    name: "ToolbarCom",
    setup() {
        const route = useRoute();
        let route_name = computed(() => route.name);
        const click_toolbar = () => {
            // eslint-disable-next-line no-undef
            $(window).scrollTo(0, 500);
        }
        return {
            click_toolbar,
            route_name
        }
    }
}
</script>

<style scoped>

</style>