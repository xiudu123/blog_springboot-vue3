<template>
<UserContentFieldCom>

    <!--搜索栏-->
    <div class="ui secondary segment form" >
        <div class="inline fields">
            <!--                    标题-->
            <div class="field">
                <!--                        <label>-->
                <input type="text" placeholder="标题" v-model="search.title">
                <!--                        </label>-->
            </div>
            <!--                    分类-->
            <div class="field">
                <!--                        <label>-->
                <span>选择分类</span>
                <select class="ui search dropdown"  v-model="search.type_id">
                    <option value="-1"> </option>
                    <option v-for="type in type_list.records" :key="type.id" :value="type.id"> {{ type.name }} </option>
                </select>
                <!--                        </label>-->
            </div>
            <!--                    置顶-->
            <div class="field">
                <span >是否置顶</span>
                <select class="ui search dropdown" v-model="search.top">
                    <option value="-1"> </option>
                    <option value="1"> 是 </option>
                    <option value="0"> 否 </option>
                </select>
            </div>
            <!--                    发布-->
            <div class="field">
                <span >是否发布</span>
                <select class="ui search dropdown" v-model="search.published">
                    <option value="-1"> </option>
                    <option value="1"> 发布 </option>
                    <option value="0"> 未发布 </option>
                </select>
            </div>
            <!--                    评论-->
            <div class="field">
                <span >开启评论</span>
                <select class="ui search dropdown" v-model="search.comment">
                    <option value="-1"> </option>
                    <option value="1"> 开启 </option>
                    <option value="0"> 未开启 </option>
                </select>
            </div>
            <!--                    搜索-->
            <div class="field">
                <button id="search-btn" type="button" class="ui teal basic button" @click="getBlogSearch(1, search.title, search.type_id, search.top, search.published, search.comment)"> <i class="search icon"></i>搜索</button>
            </div>
        </div>
    </div>

    <!--成功提示消息-->
    <div v-if="success_message" class="ui success message">
        <i class="close icon" @click="close_success_message"></i>
        <div class="">{{success_message}}</div>
    </div>

    <!--失败提示消息-->
    <div v-if="error_message" class="ui negative message">
        <i class="close icon" @click="close_error_message"></i>
        <div class="">{{error_message}}</div>
    </div>

    <!--文章栏-->
    <div class="ui segments">
        <div class="ui segment right aligned">
            <router-link :to="{name : 'user_blog_input'}" class="ui blue basic button">新增</router-link>
        </div>

        <div id="table-container">
            <table  class="ui center aligned table segment" style="width: 100%">
                <thead>
                <tr>
                    <th></th>
                    <th>标题</th>
                    <th>类型</th>
                    <th>是否置顶</th>
                    <th>是否发布</th>
                    <th>开启评论</th>
                    <th>创建时间</th>
                    <th>更新时间</th>
                    <th>操作</th>
                </tr>
                </thead>

                <tbody>
                <tr v-for="(blogs, index) in blog_list.records" :key="blogs.id">
                    <td > {{ index + 1 }} </td>
                    <td > {{ blogs.title }} </td>
                    <td > {{ blogs.typeName }} </td>
                    <td > {{ blogs.top ? "是" : "否" }} </td>
                    <td > {{ blogs.published ? "发布" : "未发布" }} </td>
                    <td > {{ blogs.comment ? "开启" : "未开启" }} </td>
                    <td > {{ blogs.createTime }} </td>
                    <td > {{ blogs.updateTime }} </td>
                    <td>
                        <button class="ui mini blue button" @click="updateBlog(blogs.id)">编辑</button>
                        <button class="ui mini red button" @click="showDeleteDia(blogs.id)">删除</button>
                    </td>
                </tr>
                </tbody>

                <tfoot>
                <tr>
                    <th colspan="9">
                        <div class="ui right floated pagination menu">

                            <!--                            上一页-->
                            <a href="#" class="icon item " style="border: none" v-if="blog_page.current !== 1" @click="getBlogSearch(blog_page.current - 1, search.title, search.type_id, search.top, search.published, search.comment)">
                                <i class="left chevron icon"></i>
                            </a>
                            <!--                            页码-->
                            <div style="padding: 0" class="ui pagination menu">
                                <a href="#" class="item" > {{blog_page.current}} </a>
                            </div>
                            <!--                            下一页-->
                            <a href="#" class="icon item" style="border: none" v-if="blog_page.current !== blog_page.total" @click="getBlogSearch(blog_page.current + 1, search.title, search.type_id, search.top, search.published, search.comment)">
                                <i  class="right chevron icon"></i>
                            </a>

                            <!--                            总页-->
                            <div class="icon item">
                                共<div> {{ blog_page.total }} </div>页
                            </div>
                        </div>
                    </th>
                </tr>
                </tfoot>
            </table>

            <div class="ui mini basic modal">
                <div class="ui icon header">
                    <i class="archive icon"></i>
                    是否删除
                </div>
                <div class="content">
                    <p>确定要删除这篇博客吗?</p>
                </div>
                <div class="actions">
                    <div class="ui red basic cancel inverted button">
                        <i class="remove icon"></i>
                        取消
                    </div>
                    <div class="ui green ok inverted button">
                        <i class="checkmark icon"></i>
                        确定
                    </div>
                </div>
            </div>
        </div>

    </div>

</UserContentFieldCom>
</template>

<script>
import UserContentFieldCom from "@/components/UserContentFieldCom";
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import {useRouter} from "vue-router";
import router from "@/router";

export default {
    name: "UserBlogManageView",
    components: {UserContentFieldCom},
    setup() {
        const route = useRouter();
        const success_message = ref("");
        const error_message = ref("");
        const blog_page = reactive({
            pre : 0,
            next : 0,
            current : 0,
            total : 0,
        });
        const blog_list = reactive({
            records: null
        });
        const type_list = reactive({
            records: null
        });
        const search = reactive({
            title : '',
            type_id : -1,
            top :-1 ,
            published : -1,
            comment: -1

        });
        const close_success_message = () => {
            success_message.value = "";
        }
        const close_error_message = () => {
            error_message.value = "";
        }

        onMounted(() => {
            document.title = "博客管理";
            getBlogSearch(1, search.title, search.type_id, search.top, search.published, search.comment);
            getTypeList();
        })


        /**
         * @description: 打开是否删除的模态框
         * @param {number} blog_id
         * @return void
         */
        const showDeleteDia = (blog_id) => {
            // eslint-disable-next-line no-undef
            $('.ui.mini.basic.modal')
                .modal({
                    onShow:function () {

                    },
                    onApprove: () => {
                      console.log("确认");
                      deleteBlog(blog_id);
                    },
                    onDeny: () => {

                    }
                })
                .modal('show');

        }

        /**
         * @description: 根据博客Id跳转修改博客页面
         * @param {number} blog_id
         * @return void
         */
        const updateBlog = (blog_id) => {
            route.push({
                name: 'user_blog_update',
                query: {
                    id: blog_id
                }
            })
        }


        /**
         * @description: API 获取所有类型
         * @return type_list(类型列表)
         */
        const getTypeList = () => {
            axios.get(process.env.VUE_APP_API_BASE_URL + "/authorize/types/get/all", {
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp => {
                if(resp.data.error === 'success') {
                    type_list.records = resp.data.data;
                }
            }).catch(() => {router.push({name:'500'})});
        }

        /**
         * @description: API 获取博客泪飙
         * @param {number} page_num
         * @param {String} title (查询博客标题)
         * @param {number} type_id (查询博客的类型Id)
         * @param {number} top (查询博客是否置顶)
         * @param {number} published (查询博客是否发布)
         * @param {number} comment (查询博客是否发布)
         * @return blog_list(博客列表), blog_page(页码信息)
         */
        const getBlogSearch = (page_num, title, type_id, top, published, comment) => {
            axios.get(process.env.VUE_APP_API_BASE_URL + "/authorize/blog/get/search", {
                params: {
                    "pageNum": page_num,
                    "title": title,
                    "typeId": type_id,
                    "top": top,
                    "published": published,
                    "comment": comment
                },
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp => {
                if(resp.data.error === "success") {
                    const data = resp.data.data;
                    blog_page.pre = data.pagePre;
                    blog_page.next = data.pageNext;
                    blog_page.current = data.pageCurrent;
                    blog_page.total = data.pageTotal;
                    blog_list.records = data.records;
                }
            }).catch(() => {router.push({name:'500'})})
        }

        /**
         * @description: API 根据博客Id删除博客
         * @param {number} blog_id
         * @return void
         */
        const deleteBlog = (blog_id) => {
            axios.post(process.env.VUE_APP_API_BASE_URL + "/authorize/blog/delete", blog_id, {
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': 'application/json',
                },
            }).then((resp) => {
                if(resp.data.error === 'success') {
                    getBlogSearch(blog_page.current, search.title, search.type_id, search.top, search.published);
                    success_message.value = "操作成功";
                }else {
                    error_message.value = resp.data.error;
                }
            }).catch(() => {
                error_message.value = "操作失败, 请稍后再试";
            })
        }



        return {
            success_message,
            error_message,
            type_list,
            blog_page,
            blog_list,
            search,

            close_success_message,
            close_error_message,
            getBlogSearch,
            getTypeList,
            showDeleteDia,
            updateBlog,
            deleteBlog,
        }
    }
}
</script>

<style scoped>

</style>