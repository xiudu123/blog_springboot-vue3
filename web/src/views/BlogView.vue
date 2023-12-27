<template>
    <ContentFieldCom>
        <div class="ui container animated fadeIn"  style="min-width: 60vw" >
            <!--图片-->
            <div class="ui top attached segment" align="center">
                <img :src="blog_info.first_picture" alt="" class="ui rounded rounded image" style="width: 800px; height: 450px">
            </div>
            <!--头部-->
            <div class="ui attached segment">
                <div class="ui horizontal link list">
                    <div class="item">
                        <img src="@/assets/img/xiu.jpg" alt="" class="ui avatar image">
                        <div class="content"><a href="#" target="_blank" class="header"  >{{ blog_info.username }}</a></div>
                    </div>
                    <div class="item">
                        <i class="calendar icon"></i><span>{{ blog_info.update_time }}</span>
                    </div>
                    <div class="item">
                        <i class="eye icon"></i> <span>{{ blog_info.views }}</span>
                    </div>
                    <!--                    <div class="item">-->
                    <!--                        <i class="comment outline icon"></i> <span>2222</span>-->
                    <!--                    </div>-->
                </div>
            </div>
            <!--标题-->
            <div class="ui attached padded segment " align="center">
                <h1>{{blog_info.title}}</h1>
            </div>
            <!--内容-->
            <div class="ui attached padded segment ">
                {{blog_info.content}}
            </div>
            <!--分类-->
            <div class="ui attached padded segment">
                <div class="ui basic teal label" > {{ blog_info.type_name }} </div>
            </div>


            <!--博客信息-->
            <div class="ui attached positive message">
                <div class="ui middle aligned divided grid">
                    <div class="three wide column" align="center">
                        <img src="@/assets/img/xiu.jpg" alt="" class="ui rounded bordered image" style="width: 110px; border-radius: 50%">
                    </div>
                    <div class="thirteen wide column" style="color: black">
                        <ui class="list">
                            <li>作者：<span>{{ blog_info.username }}</span></li>
                            <li>发布时间：<span>{{ blog_info.create_time }}</span></li>
                            <li>更新时间：<span>{{ blog_info.update_time }}</span></li>
                        </ui>

                    </div>

                </div>
            </div>
            <!--评论区域-->
            <div id="comment-content" class="ui bottom attached segment" v-if="blog_info.comment">

                <div>
                    <div class="ui segment">
                        <div class="ui threaded comments" style="max-width: 100%;">
                            <h3 class="ui dividing header">评论</h3>
                            <div class="comment" v-for="comment in comment_info.comments" :key="comment.id">
                                <a class="avatar">
                                    <img src="@/assets/img/xiu.jpg" alt="" >
                                </a>
                                <div class="content" :id="'comment' + comment.id" :class="{'flash-effect': comment.id === replyCommentId}">
                                    <a class="author">
                                        <span >{{ comment.nickname }}</span>
                                        <div class="ui mini basic teal left pointing label m-padded-mini" v-if="comment.userId === blog_info.user_id">
                                            博主
                                        </div>
                                    </a>
                                    <div class="metadata">
                                        <span class="date" >{{ comment.createTime }}</span>
                                    </div>
                                    <div class="text">
                                        {{ comment.content }}
                                    </div>
                                    <div class="actions">
                                        <a class="reply" @click="reply(comment.id, comment.id)">回复</a>
                                    </div>
                                </div>
                                <!--子集评论-->
                                <div class="comments" v-if="comment_info.replyComments[comment.id]">
                                    <div class="comment" v-for="reply_comment in comment_info.replyComments[comment.id]" :key="reply_comment.id" :class="{'flash-effect': reply_comment.id === replyCommentId}">
                                        <a class="avatar">
                                            <img src="https://unsplash.it/100/100?image=1005" alt="">
                                        </a>
                                        <div class="content" :id="'comment' + reply_comment.id">
                                            <span> {{ reply_comment.nickname }}</span>
                                            <div class="ui mini basic teal left pointing label m-padded-mini" v-if="reply_comment.userId === blog_info.user_id">
                                                博主
                                            </div>
                                            &nbsp;
                                            <a class="author" >
                                                &#64;
                                                <span class="m-teal" @click="clickReply(reply_comment.parentId)"> {{ comment_info.idToName[reply_comment.parentId] }}</span>
                                                <div class="ui mini basic teal left pointing label m-padded-mini" v-if="comment_info.idToUserId[reply_comment.parentId] === blog_info.user_id">
                                                    博主
                                                </div>
                                            </a>

                                            <div class="metadata">
                                                <span class="date">{{ reply_comment.createTime }}</span>
                                            </div>
                                            <div class="text">
                                                {{ reply_comment.content }}
                                            </div>
                                            <div class="actions">
                                                <a class="reply" @click="reply(comment.id, reply_comment.id)">回复</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>


                            </div>
                        </div>
                    </div>
                </div>
            </div>
            <!--留言区域-->
            <div id="comment-form" class="ui form bottom segment" v-if="blog_info.comment">
                <div class="field">
                    <label>
                        <textarea class="comment-content" placeholder="与人善言，暖于布帛；伤人以言，深于矛戟..." v-model="comment_post.content"></textarea>
                    </label>
                </div>
                <div class="fields">
                    <div class="field m-mobile-wide m-margin-button-small">
                        <div class="ui left icon input">
                            <i class="user icon"></i>
                            <input type="text" name="nickname" placeholder="姓名" v-model="comment_post.nickname" :disabled="$store.state.user.username!==''">
                        </div>
                    </div>
                    <div class="field m-mobile-wide m-margin-button-small">
                        <div class="ui left icon input">
                            <i class="mail icon"></i>
                            <input type="email" name="email" placeholder="邮箱" v-model="comment_post.email" :disabled="$store.state.user.email!==''" >
                        </div>
                    </div>
                    <div class="field m-mobile-wide m-margin-button-small">
                        <div id="commentPost-btn" type="button" class="ui blue button m-mobile-wide" @click="submitComment"><i class="edit icon"></i> 发布</div>
                    </div>
                </div>
            </div>
        </div>
    </ContentFieldCom>

    <div class="ui vertical icon buttons m-padded-tb-large m-fixed m-right-bottom">
        <button id="toc" type="button" class="ui teal button" @mouseenter="mouse_toc_menu">目录</button>
        <div id="comment-button" class="ui teal button"  v-if="blog_info.comment" @click="click_comment_button">评论</div>
        <div id="comment-reply" class="ui teal button" v-if="blog_info.comment" @click="click_comment_reply">留言</div>
        <div id="toolbar" class="ui icon button" @click="click_toolbar"><i class="chevron up icon"></i></div>
    </div>

    <div class="ui toc-container flowing popup transition hidden" style="width: 250px !important;">
        <ol class="js-toc">

        </ol>
    </div>
</template>

<script>
import ContentFieldCom from "@/components/ContentFieldCom";
import {onMounted, reactive, ref} from "vue";
import axios from "axios";
import {useRoute} from "vue-router";
import {useStore} from "vuex";
import router from "@/router";
export default {
    name: "BlogView",
    components: {ContentFieldCom},
    setup() {
        const route = useRoute();
        const store = useStore();

        const replyCommentId = ref(null);
        const blog_info = reactive({
            id : null,
            first_picture : null,
            title : null,
            comment : null,
            content : null,
            create_time : null,
            overview : null,
            published : null,
            top : null,
            type_id : null,
            type_name : null,
            update_time : null,
            user_id : null,
            username : null,
            views : null,
        });
        const comment_info = reactive({
            idToName : null,
            idToUserId : null,
            comments : null,
            replyComments : null,
        });
        const comment_post = reactive({
            top_comment_id: -1,
            parent_id: -1,
            nickname: store.state.user.username,
            email: store.state.user.email,
        });
        /**
         * @description: 点击评论按钮跳转到评论位置
         * @return void
         */
        const click_comment_button = () => {
            // eslint-disable-next-line no-undef
            $(window).scrollTo('#comment-content', 500);
        }

        /**
         * @description: 点击留言按钮跳转到留言位置
         * @return void
         */
        const click_comment_reply = () => {
            // eslint-disable-next-line no-undef
            $(window).scrollTo('#comment-form', 500);
        }

        /**
         * @description: 点击顶部按钮回到页面顶部
         * @return void
         */
        const click_toolbar = () => {
            // eslint-disable-next-line no-undef
            $(window).scrollTo(0, 500);
        }

        /**
         * @description: 目录弹出
         * @return void
         */
        const mouse_toc_menu = () => {
            // eslint-disable-next-line no-undef
            $('#toc').popup({
                // eslint-disable-next-line no-undef
                popup : $('.toc-container.popup'),
                on : 'click',
                position: 'left center'
            });
        }

        /**
         * @description: 回复评论
         * @param {number} top_comment_id
         * @param {number} parent_id
         * @return void
         */
        const reply = (top_comment_id, parent_id) => {
            comment_post.topCommentId = top_comment_id;
            comment_post.parentId = parent_id;
            // eslint-disable-next-line no-undef
            $(".comment-content").attr("placeholder", "@" + comment_info.idToName[parent_id]).focus();
            // eslint-disable-next-line no-undef
            $(window).scrollTo($('#comment-form'),500);
        }

        /**
         * @description: 提交评论
         * @return viod
         */
        const submitComment = () => {
            comment_post.blogId = blog_info.id;
            comment_post.userId = store.state.user.id;
            addComment(comment_post);
        }

        /**
         * @description: 跳转到被回复的评论位置
         * @param {number} parent_id
         * @return void
         */
        const clickReply = (parent_id) => {
            // eslint-disable-next-line no-undef
            $(window).scrollTo("#comment" + parent_id, 500, { offset: -100});
            replyCommentId.value = parent_id;
            setTimeout(() => {
                replyCommentId.value = null;
            }, 3000);
        }

        /**
         * @description: API 根据博客Id 获取博客信息
         * @param {number} blog_id
         * @return blog_info(博客的所有信息);
         */
        const getBlog = (blog_id) => {
            axios.get(process.env.VUE_APP_API_BASE_URL + "/blog/id", {
                params: {
                    "blogId": blog_id,
                },
                headers: {
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp => {
                if(resp.data.error === "success") {
                    const data = resp.data.data;
                    blog_info.id = data.id;
                    blog_info.first_picture = data.firstPicture;
                    blog_info.title = data.title;
                    blog_info.comment = data.comment;
                    blog_info.content = data.content;
                    blog_info.create_time = data.createTime;
                    blog_info.overview = data.overview;
                    blog_info.published = data.published;
                    blog_info.top = data.top;
                    blog_info.type_id = data.typeId;
                    blog_info.type_name = data.typeName;
                    blog_info.update_time = data.updateTime;
                    blog_info.user_id = data.userId;
                    blog_info.username = data.username;
                    blog_info.views = data.views;
                }else {
                    let routeUrl = router.resolve({
                        name: '404',
                    })
                    window.open(routeUrl.href, '_self');
                }
            }).catch(() => {router.push({name:'500'})});
        }

        /**
         * @description: API 根据博客Id 获取评论信息
         * @param {number} blog_id
         * @return comment_info(评论信息)
         */
        const getComment = (blog_id) => {
            axios.get(process.env.VUE_APP_API_BASE_URL + "/comments/obtain", {
                params: {
                    "blogId": blog_id,
                },
                headers: {
                    'Content-Type': "application/x-www-form-urlencoded",
                },
            }).then(resp => {
                if(resp.data.error === 'success') {
                    const data = resp.data.data;
                    comment_info.idToName = data.idToName;
                    comment_info.idToUserId = data.idToUserId;
                    comment_info.comments = data.comments;
                    comment_info.replyComments = data.replyComments;
                }

            }).catch(() => {router.push({name:'500'})})
        }

        /**
         * @description: API 添加评论
         * @param {object} comment
         * @return void
         */
        const addComment = (comment) => {
            axios.post(process.env.VUE_APP_API_BASE_URL + "/comments/submit", comment, {
                headers: {
                    'Content-Type': "application/json",
                },
            }).then(() => {
                window.location.reload();
            }).catch(() => {router.push({name:'500'})})
        }

        onMounted(() => {
            // eslint-disable-next-line no-undef
            tocbot.init({
                // Where to render the table of contents.
                tocSelector: '.js-toc',
                // Where to grab the headings to build the table of contents.
                contentSelector: '.js-toc-content',
                // Which headings to grab inside the contentSelector element.
                headingSelector: 'h1, h2, h3, h4, h5',
            });

            getBlog(route.query.id);
            getComment(route.query.id);
            setTimeout(() => {
                comment_post.nickname = store.state.user.username;
                comment_post.email = store.state.user.email;
            }, 1000);
        })

        return {
            blog_info,
            comment_info,
            comment_post,
            replyCommentId,

            click_comment_button,
            click_comment_reply,
            click_toolbar,
            clickReply,
            mouse_toc_menu,
            reply,
            submitComment
        }
    }
}
</script>

<style scoped>
/* CSS 中定义闪动特效 */
.flash-effect {
    animation: flash 3s infinite running;
}
@keyframes flash {
    from {
        background-color: #E9E9E9;
    }
    to {
        background-color: white;
    }
}
</style>