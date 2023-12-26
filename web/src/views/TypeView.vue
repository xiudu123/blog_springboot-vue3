<template>
    <ContentFieldCom>
        <div class="ui segment m-padded-tb-large m-opacity">
            <div class="ui container center aligned">
                <div class="ui labeled button m-margin-tb-tiny" v-for="type in type_list.records" :key="type.id" @click="clickType(type.id)">
                    <div class="ui basic button" > {{ type.name }} </div>
                    <div class="ui basic  left pointing label"> {{ type.count }} </div>
                </div>
            </div>
        </div>

        <div class="ui top attached green segment m-opacity ">
            <!--博客-->
            <div class="ui padded segment m-opacity" v-for="blog in blog_list.records" :key="blog.id">

                <div class="ui mobile reversed stackable grid  m-margin-top-large" style="min-width: 50vw;" @click="clickBlog(blog.id)">
                    <!--博文信息-->
                    <div class="eleven wide column left floated">
                        <h3 class="ui header" >
                            <span class="m-black m-title-font" style="cursor: pointer"> {{ blog.title }} </span>
                            <span class="ui mini blue basic button" style="margin-left: 10px" v-if="blog.top" >置顶</span>
                        </h3>
                        <p class="m-text m-margin-top-max" >{{ blog.overview }}</p>
                        <div class="ui m-margin-top-max stackable grid">
                            <div class="eleven wide column">
                                <div class="ui mini horizontal link list">
                                    <div class="item">
                                        <img src="@/assets/img/xiu.jpg" alt="" class="ui avatar image">
                                        <div class="content"><a href="#" class="header" >{{ blog.username }}</a></div>
                                    </div>
                                    <div class="item">
                                        <i class="calendar icon"></i><span>{{ blog.updateTime }}</span>
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
                                <div class="ui teal basic label m-padded-tiny m-text-thin">{{ blog.typeName }}</div>
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
                <a class="ui teal basic button item" @click="getBlog(blog_page.current - 1, type_now.value)">上一页</a>
            </div>

            <div class="ten wide column center aligned" style="margin: auto">
                <p> <span> {{ blog_page.current }} </span> / <span> {{ blog_page.total }} </span> </p>
            </div>
            <div class="three wide column center aligned">
                <a class="ui teal basic button item" @click="getBlog(blog_page.current + 1, type_now.value)">下一页</a>
            </div>
        </div>
    </ContentFieldCom>
</template>

<script>
import ContentFieldCom from "@/components/ContentFieldCom";
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import router from "@/router";
export default {
    name: "TypeView",
    components: {ContentFieldCom},
    setup() {
        const type_list = reactive({});
        const type_now = ref("");
        const blog_list = reactive({});
        const blog_page = reactive({
            'next' : 0,
            'pre' : 0,
            'current' : 0,
            'total' : 0,
        });
        const getTypes = () => {
            axios.get(process.env.VUE_APP_API_BASE_URL + "/types", {
                headers: {
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp => {
                if(resp.data.error === "success")  {
                    type_list.records = resp.data.data.records;
                }
            }).catch()
        }
        const getBlog = (page_num, type_id) => {
            axios.get(process.env.VUE_APP_API_BASE_URL + "/type", {
                params: {
                    "pageNum" : page_num,
                    "typeId" : type_id
                },
                headers: {
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp => {
                if(resp.data.error === "success") {
                    const data = resp.data.data;
                    blog_list.records = data.pageInfo.records;
                    blog_page.next = data.nextPage;
                    blog_page.pre = data.prePage;
                    blog_page.current = data.pageInfo.current;
                    blog_page.total = data.pageInfo.pages;
                }
            }).catch();
        }

        const clickType = (type_id) => {
            type_now.value = type_id;
            getBlog(1, type_now.value);
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
            getTypes()
        })

        return {
            type_list,
            type_now,
            blog_list,
            blog_page,
            getBlog,
            clickType,
            clickBlog
        }
    }
}
</script>

<style scoped>

</style>