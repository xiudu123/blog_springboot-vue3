<template>
    <nav id="my-nav">
        <div class="ui container">
            <div class="ui inverted secondary menu stackable">
                <h1 class="ui blue header item">后台管理</h1>
                <router-link :to="{name : 'user_blog_manage'} " class="item my-item m-mobile-hide" :class="route_name === 'user_blog_manage' ? 'active' : ''" @click = "click_link"><i class="folder open outline icon"></i>文章管理</router-link>
                <router-link :to="{name : 'user_blog_input'} "  class="item my-item m-mobile-hide" :class="route_name === 'user_blog_input' ? 'active' : ''"  @click = "click_link"><i class="file alternate outline icon"></i>文章发布</router-link>
                <router-link :to="{name : 'user_type_manage'} " class="item my-item m-mobile-hide" :class="route_name === 'user_type_manage' ? 'active' : ''" @click = "click_link"><i class="clone outline icon"></i>分类管理</router-link>
                <div class="right my-item m-mobile-hide menu">
                    <div class="item">
                        <div class="ui dropdown item">
                            <div class="text">
                                <img class="ui avatar image" src="@/assets/img/xiu.jpg" alt="">
                                锈渎
                            </div>
                            <i class="dropdown icon"></i>

                            <div class="menu">
                                <a href="#" class="item">注销</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <button id="menu-toggle" class="ui menu black button icon my-menu m-mobile-show" @click="click_menu">
            <i class="sidebar icon"></i>
        </button>
    </nav>

</template>

<script>
import {useRoute} from "vue-router";
import {computed} from "vue";

export default {
    name: "NavbarUserCom",
    setup() {
        const route = useRoute();
        let route_name = computed(() => route.name);
        const click_menu = () => {
            // eslint-disable-next-line no-undef
            $(".my-item").toggleClass("m-mobile-hide") // 有这个"m-mobile-hide"类就加上这个类否则就删去这个类
        }
        const click_link = () => {
            // eslint-disable-next-line no-undef
            $(".my-item").addClass("m-mobile-hide")
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
#my-nav {
    width: 100%;
    min-height: 60px;
    opacity: 1;
    position: fixed;
    background-color: rgba(0,0,0,1);
    /*z-index: 1024;*/
    z-index: 100000;
    top: 0;
    left: 0;
    font-size: 15px;
}

.my-menu{
    position: absolute;
    top: 0;
    right: 0;
}

</style>