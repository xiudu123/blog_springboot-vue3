<template>

    <ContentFieldCom>
        <div class="ui middle aligned container stacked grid">
            <div class="ui vertical segment">

                <!--博客-->
                <div class="ui padded segment m-opacity" style="background-color: #F7F7F7" v-for="blog in blog_list.records" :key="blog.id">

                    <div class="ui mobile reversed stackable grid  m-margin-top-large" @click="clickBlog(blog.id)">
                        <!--博文信息-->
                        <div class="eleven wide column">
                            <h3 class="ui header" >
                                <span class="m-black m-title-font" style="cursor: pointer"> {{blog.title}} </span>
                                <span class="ui mini blue basic button" style="margin-left: 10px" v-if="blog.top">置顶</span>
                            </h3>
                            <p class="m-text m-margin-top-max" style="min-width: 50vw;" >{{ blog.overview }}</p>
                            <div class="ui m-margin-top-max stackable grid">
                                <div class="eleven wide column">
                                    <div class="ui mini horizontal link list">
                                        <div class="item">
                                            <img src="@/assets/img/xiu.jpg" alt="" class="ui avatar image">
                                            <div class="content"><a href="#" class="header" >xiu du</a></div>
                                        </div>
                                        <div class="item">
                                            <i class="calendar icon"></i><span> {{blog.updateTime}} </span>
                                        </div>
                                        <div class="item">
                                            <i class="eye icon"></i> <span>{{ blog.views }}</span>
                                        </div>
                                        <!--                                        <div class="item">-->
                                        <!--                                            <i class="comment outline icon"></i> <span>2222</span>-->
                                        <!--                                        </div>-->
                                    </div>
                                </div>
                                <div class="right aligned five wide column">
                                    <a href="#" target="_blank" class="ui teal basic label m-padded-tiny m-text-thin">{{ blog.typeName }}</a>
                                </div>
                            </div>
                        </div>
                        <!--博文图片-->
                        <div class="five wide column">
                            <img :src="blog.firstPicture" alt="" class="ui rounded image" style="width: 400px; height: 200px">
                        </div>

                    </div>
                </div>

                <!-- 分页-->
                <div class="ui bottom segment m-opacity stackable grid">
                    <div class="three wide column center aligned">
                        <a href="#" class="ui teal basic button item" @click="getBlog(blog_page.current - 1)">上一页</a>
                    </div>

                    <div class="ten wide column center aligned" style="margin: auto">
                        <p> <span> {{ blog_page.current }} </span> / <span> {{ blog_page.total }} </span> </p>
                    </div>
                    <div class="three wide column center aligned">
                        <a href="#" class="ui teal basic button item" @click="getBlog(blog_page.current + 1)">下一页</a>
                    </div>
                </div>
            </div>

        </div>
    </ContentFieldCom>
</template>

<script>
import ContentFieldCom from "@/components/ContentFieldCom";
import {onMounted, reactive} from "vue";
import axios from "axios";
import router from "@/router";
export default {
    name: "IndexView",
    components: {ContentFieldCom},
    setup() {
        const blog_list = reactive({});
        const blog_page = reactive({});
        const getBlog = (page_num) => {
            axios.get(process.env.VUE_APP_API_BASE_URL + "/index", {
                params: {
                    "pageNum": page_num,
                },
                headers: {
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            })
                .then(resp =>  {
                    const data = resp.data.data;
                    blog_list.records = data.pageInfo.records;
                    blog_page.pre = data.prePage;
                    blog_page.next = data.nextPage;
                    blog_page.current = data.pageInfo.current;
                    blog_page.total = data.pageInfo.pages;
                }).catch()
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
            getBlog(1)
        })

        return {
            getBlog,
            clickBlog,
            blog_list,
            blog_page
        }
    }
}
</script>

<style scoped>

</style>