<template>
    <nav id="my-nav">
        <div class="ui container">
            <div class="ui inverted secondary menu stackable">

                <router-link :to = "{name: 'image'}"><h1 class="ui blue header item">锈渎</h1> </router-link>
                <router-link :to = "{name: 'index'}" class="item m-item m-mobile-hide" :class="route_name === 'index' ? 'active' : ''" @click = "click_link"><i class="home icon"></i>首页</router-link>
                <router-link :to = "{name: 'type'}" class="item m-item m-mobile-hide" :class="route_name === 'type' ? 'active' : '' " @click = "click_link"><i class="clone outline icon"></i>分类</router-link>
                <router-link :to = "{name: 'archives'}" class="item m-item m-mobile-hide" :class="route_name === 'archives' ? 'active' : '' " @click = "click_link"><i class="clock outline icon"></i>时间轴</router-link>
                <router-link :to = "{name: 'about'}" class="item m-item m-mobile-hide" :class="route_name === 'about' ? 'active' : '' " @click = "click_link"><i class="info icon"></i>关于我</router-link>
                <div class="right item m-item m-mobile-hide">
                    <form action="#" name="search" method="get" target="_blank">
                        <div class="ui icon inverted transparent input">
                            <input type="text" name="query" placeholder="搜索..." id="search">
                            <label for="search">
                                <i class="search link icon"></i>
                            </label>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <button id="menu-toggle" class="ui menu black button icon m-right-top m-mobile-show" @click="click_menu">
            <i class="sidebar icon"></i>
        </button>
    </nav>
</template>

<script>
import { useRoute } from 'vue-router';
import { computed } from "vue";
export default {
    name: "NavbarCom",
    setup() {
        const route = useRoute();
        let route_name = computed(() => route.name);

        const click_menu = () => {
            // eslint-disable-next-line no-undef
            $(".m-item").toggleClass("m-mobile-hide") // 有这个"m-mobile-hide"类就加上这个类否则就删去这个类
        }
        const click_link = () => {
            // eslint-disable-next-line no-undef
            $(".m-item").addClass("m-mobile-hide")
        }


        return {
            route_name,
            click_menu,
            click_link,
        }
    }
}
</script>

<style scoped>
nav {
    width: 100%;
    min-height: 60px;
    opacity: 0.5;
    position: fixed;
    background-color: rgba(0,0,0,1);
    /*z-index: 1024;*/
    z-index: 100000;
    top: 0;
    left: 0;
    font-size: 15px;
}
</style>