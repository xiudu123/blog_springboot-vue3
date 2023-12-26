<template>
    <footer class="ui inverted vertical segment  m-padded-tb m-opacity m-margin-top-big">
        <div class="ui center aligned container">
            <div class="ui inverted divided stackable grid">
                <div class="five wide column">
                    <h4 class="ui inverted header m-opacity-small">联系我</h4>
                    <div class="ui inverted link list">
                        <div class="item">QQ: <span >844506672</span> </div>
                    </div>
                </div>
                <div class="six wide column">
                    <h4 class="ui inverted header m-opacity-small">最新博客</h4>
                    <div id="newBlog-container" v-for="blog in blog_list.records" :key="blog.id">
                        <div class="ui inverted link list">
                            <div class="item my-blog-title" @click="clickBlog(blog.id)"> {{ blog.title }} </div>
                        </div>
                    </div>
                </div>
                <div class="five wide column">
                    <h4 class="ui inverted header m-opacity-small">小栈信息</h4>
                    <div id="message-container">
                        <div class="ui inverted link list">
                            <div class="item">
                                文章总数: <div class="ui orange header m-inline-block">&nbsp;{{ blog_count }}&nbsp;</div> 篇
                            </div>
                        </div>
                    </div>

                </div>
            </div>

            <div class="ui inverted section divider"></div>
            <div class="ui orange header m-text-thin m-text-spaced m-opacity-small">
                我的小栈已营业:
                <span id="run_time"></span>
            </div>

        </div>
    </footer>
</template>

<script>
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import router from "@/router";

export default {
    name: "FooterCom",
    setup(){
        const blog_count = ref();
        const blog_list = reactive({});

        const getBlogCount = () => {
            axios.get(process.env.VUE_APP_API_BASE_URL + "/footer/message", {
                headers: {
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp => {
                blog_count.value = resp.data.data;
            })
        }

        const getBlogList = () => {
            axios.get(process.env.VUE_APP_API_BASE_URL + "/footer/newBlog", {
                headers: {
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp => {
                blog_list.records = resp.data.data;
            })
        }

        const clickBlog = (blog_id) => {
            let routeUrl = router.resolve({
                name: 'blog',
                query: {
                    id: blog_id
                }
            })
            window.open(routeUrl.href, '_blank');
        }

        onMounted(() => {
            getBlogCount();
            getBlogList()
        })

        return{
            blog_count,
            blog_list,
            clickBlog
        }
    }
}
</script>

<style scoped>

.my-blog-title {
    border-radius:30px;
    cursor: pointer;/*鼠标变成手指样式*/
    transition: all 0.6s;/*所有属性变化在0.6秒内执行动画*/
}
.my-blog-title:hover {
    transform: scale(1.3);/*鼠标放上之后元素变成1.4倍大小*/
}
</style>