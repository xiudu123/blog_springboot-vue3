<template>
    <UserContentFieldCom>
        <div class="ui segment">
            <!--失败提示消息-->
            <div v-if="error_message" class="ui negative message">
                <i class="close icon" @click="close_error_message"></i>
                <div class="">{{error_message}}</div>
            </div>

            <form id="blog-form" action="#" method="post" class="ui form">
                <input type="hidden" name="published">
                <div class="required field">
                    <input type="text" name="title" placeholder="标题" v-model="blog_info.title">
                </div>

                <div class="field">
                    <div class="fourteen wide column">
                        <div class="ui left labeled action input">
                            <label class="ui compact teal basic label">分类</label>
                            <div class="ui fluid selection dropdown my-type"  @mouseup="typeMenu" >
                                <input type="hidden" value="-1" name="typeId">
                                <i class="dropdown icon"></i>
                                <div class="default text">{{ blog_info.typeName }}</div>
                                <div class="menu">
                                    <div class="item" v-for="type in type_list.records" :key="type.id" :data-value="type.id"> {{ type.name }} </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="field">
                    <div class="ui left labeled input">
                        <label class="ui teal basic label">首图</label>
                        <input type="text" name="firstPicture" placeholder="首图引用地址" v-model="blog_info.firstPicture">
                    </div>
                </div>

                <div class="field">
                    <div id="md-content">
                        <textarea name="content"  cols="30" rows="10" placeholder="请编辑博客内容..." v-model="blog_info.content"></textarea>
                    </div>
                </div>

                <div class="field">
                    <div>博客描述编写</div>
                    <label>
                        <textarea name="overview"  cols="30" rows="5" placeholder="请编辑博客描述..." v-model="blog_info.overview"></textarea>
                    </label>
                </div>

                <div align="right" style="margin-right: 1em">
                    <div class="ui checkbox">
                        <input type="checkbox" id="top" name="top" class="hidden" v-model="blog_info.top">
                        <label for="top">置顶</label>
                    </div>
                    &emsp;
                    <div class="ui checkbox">
                        <input type="checkbox" id="comment" name="comment" class="hidden" v-model="blog_info.comment">
                        <label for="comment">开启评论</label>
                    </div>
                </div>

                <div class="ui right aligned container">
                    <button type="button" class="ui button" onclick="window.history.go(-1)">返回</button>
                    <button type="button" id="save-btn" class="ui secondary button" @click="noPublish">保存</button>
                    <button type="button" id="publish-btn" class="ui blue button" @click="submit">发布</button>
                </div>
            </form>
        </div>
    </UserContentFieldCom>
</template>

<script>
import UserContentFieldCom from "@/components/UserContentFieldCom";
import {onBeforeMount, reactive, ref} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";
export default {
    name: "UserBlogInputView",
    components: {UserContentFieldCom,},
    setup() {
        const router = useRoute();
        const error_message = ref("");
        const type_list = reactive({});
        const blog_info = reactive({
            id: -1,
            title: "",
            firstPicture: "",
            content: "",
            overview: "",
            top: false,
            comment: false,
            published: true,
            typeName: "",
        });
        const close_error_message = () => {
            error_message.value = "";
        }

        const typeMenu = () => {
            // eslint-disable-next-line no-undef
            $(".ui.dropdown").dropdown();
        }

        const getTypeList = () => {
            axios.get("http://127.0.0.1:3000/authorize/types/get/all", {
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp => {
                type_list.records = resp.data.data;
            }).catch();
        }

        const submit = () => {
            // eslint-disable-next-line no-undef
            const typeId = $(".ui.dropdown.my-type").dropdown('get value');
            if(typeId !== "-1") blog_info.typeId = typeId;
            axios.post("http://127.0.0.1:3000/authorize/blog/update", blog_info, {
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': 'application/json',
                },
            }).then((resp) => {
                console.log(resp);
                if(resp.data.error === "success") window.history.go(-1);
                else {
                    error_message.value = resp.data.error;
                }
            }).catch()
        }


        const noPublish = () => {
            blog_info.published = false;
            submit();
        }

        const getBlog = () => {
            blog_info.id = router.query.id;
            axios.get("http://127.0.0.1:3000/authorize/blog/get/one", {
                params: {
                    "blogId" : blog_info.id,
                },
                headers: {
                    "satoken": localStorage.getItem("token"),
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp => {
                if(resp.data.error === "success") {
                    const data = resp.data.data;
                    blog_info.title = data.title;
                    blog_info.typeId = data.typeId;
                    blog_info.firstPicture = data.firstPicture;
                    blog_info.content = data.content;
                    blog_info.overview = data.overview;
                    blog_info.top = data.top;
                    blog_info.comment = data.comment;
                    blog_info.published = true;
                    blog_info.typeName = data.typeName;
                }

            }).catch()
        }

        onBeforeMount(() => {
            getBlog()
            getTypeList();
        })

        return {
            error_message,
            type_list,
            blog_info,
            getTypeList,
            close_error_message,
            typeMenu,
            noPublish,
            submit
        }

    }
}
</script>

<style scoped>

</style>