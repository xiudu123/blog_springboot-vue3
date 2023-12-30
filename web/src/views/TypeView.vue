<template>
    <ContentFieldCom>
        <div class="ui segment m-padded-tb-large m-opacity">
            <div class="ui container center aligned">
                <div class="ui labeled button m-margin-tb-tiny" v-for="type in type_list.records" :key="type.id" @click="clickType(type.name)">
                    <div class="ui basic button" > {{ type.name }} </div>
                    <div class="ui basic  left pointing label"> {{ type.count }} </div>
                </div>
            </div>
        </div>

        <div class="ui top attached green segment m-opacity ">
            <!--博客-->
            <div class="ui padded segment m-opacity my-blog-div" v-for="blog in blog_list.records" :key="blog.id">

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
                        <img :src="blog.firstPicture" alt="" class="ui rounded image" style="width: 400px; height: 200px">
                    </div>

                </div>

            </div>

        </div>
        <!-- 分页-->
        <div class="ui bottom segment m-opacity stackable grid">
            <div class="three wide column center aligned">
                <a class="ui teal basic button item" @click="pageTurning(query_info.type_name, blog_page.pre)">上一页</a>
            </div>

            <div class="ten wide column center aligned" style="margin: auto">
                <p> <span> {{ blog_page.current }} </span> / <span> {{ blog_page.total }} </span> </p>
            </div>
            <div class="three wide column center aligned">
                <a class="ui teal basic button item" @click="pageTurning(query_info.type_name, blog_page.next)">下一页</a>
            </div>
        </div>
    </ContentFieldCom>
</template>

<script>
import ContentFieldCom from "@/components/ContentFieldCom";
import {onMounted, reactive} from "vue";
import axios from "axios";
import router from "@/router";
import {useRoute} from "vue-router";
export default {
    name: "TypeView",
    components: {ContentFieldCom},
    setup() {
        const route = useRoute();
        const type_list = reactive({
            records : null,
            name_to_id: null
        });
        const blog_list = reactive({
            records : null,
        });
        const blog_page = reactive({
            next : 0,
            pre : 0,
            current : 0,
            total : 0,
        });
        const query_info = reactive({
            type_id: 0,
            type_name: "",
            page_num: 1,
        });

        onMounted(() => {
            document.title = "标签";
            getTypes()
        })

        /**
         * @description: 通过点击标签获取所属博客
         * @param {String} type_name
         * @return void
         */
        const clickType = (type_name) => {
            pageTurning(type_name, 1);
        }

        /**
         * @description: 通过点击博客跳转到博客页面
         * @param {number} blog_id
         * @return {void} 页面至博客跳转
         */
        const clickBlog = (blog_id) => {
            let routeUrl = router.resolve({
                name: 'blog',
                query: {
                    details: blog_id
                }
            })
            window.open(routeUrl.href, '_blank');
        }

        /**
         * @description: 刷新页面, 根据传参获取博客列表
         * @return void
         */
        const refresh = () => {
            if(route.query.type_name) {
                query_info.type_name = route.query.type_name;
                query_info.type_id = type_list.name_to_id[route.query.type_name];
            }
            if(route.query.page_num)query_info.page_num = route.query.page_num;
            getBlogList(query_info.type_id, query_info.page_num);
        }

        /**
         * @description: 跳转上一页下一页（将参数显示在url上）
         * @param {String} type_name
         * @param {String} page_num
         * @return void
         */
        const pageTurning = (type_name, page_num) => {
            const routeUrl = router.resolve({
                name: 'type',
                query: {
                    type_name: type_name,
                    page_num: page_num
                }
            })
            window.open(routeUrl.href, '_self');
        }

        /**
         * @description: API 获取所有类型
         * @return type_list(所有标签的集合)
         */
        const getTypes = () => {
            axios.get(process.env.VUE_APP_API_BASE_URL + "/types", {
                headers: {
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp => {
                if(resp.data.error === "success")  {
                    type_list.records = resp.data.data.records;
                    type_list.name_to_id = resp.data.data.nameToId;
                    refresh();
                }
            }).catch(() => {router.push({name:'500'})})
        }

        /**
         * @description: API 获取标签下所属的博客
         * @param {string | LocationQueryValue[]} page_num
         * @param {string | LocationQueryValue[]} type_id
         * @return blog_list(博客集合), blog_page(页数信息)
         */
        const getBlogList = (type_id, page_num) => {
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
                    blog_list.records = data.records;
                    blog_page.next = data.pageNext;
                    blog_page.pre = data.pagePre;
                    blog_page.current = data.pageCurrent;
                    blog_page.total = data.pageTotal;
                }
            }).catch(() => {router.push({name:'500'})});
        }

        return {
            type_list,
            blog_list,
            blog_page,
            query_info,

            clickType,
            clickBlog,
            pageTurning
        }
    }
}
</script>

<style scoped>
.my-blog-div:hover {
    background-color: #F0F0F2;
}
.my-blog-div {
    background-color: #FFFFFF;
}
</style>