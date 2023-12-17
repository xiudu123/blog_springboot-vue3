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
                    <input type="text" name="title" placeholder="标题">
                </div>

                <div class="field">
                    <div class="fourteen wide column">
                        <div class="ui left labeled action input">
                            <label class="ui compact teal basic label">分类</label>
                            <div class="ui fluid selection dropdown"  style="z-index: 10001">
                                <input type="hidden" value="-1" name="typeId">
                                <i class="dropdown icon"></i>
                                <div class="default text">选择分类</div>
                                <div class="menu">
                                    <div class="item">只</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="field">
                    <div class="fourteen wide column">
                        <div class="ui left labeled action input">
                            <label class="ui compact teal basic label">标签</label>
                            <div class="ui fluid multiple selection dropdown"  style="z-index: 10000">
                                <input type="hidden" name="tagIds">
                                <i class="dropdown icon"></i>
                                <div class="default text">选择标签</div>
                                <div class="menu">
                                    <div class="item">只</div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>

                <div class="field">
                    <div class="ui left labeled input">
                        <label class="ui teal basic label">首图</label>
                        <input type="text" name="firstPicture" placeholder="首图引用地址">
                    </div>
                </div>

                <div class="field">
                    <div id="md-content">
                        <textarea name="content"  cols="30" rows="10" placeholder="请编辑博客内容..."></textarea>
                    </div>
                </div>

                <div class="field">
                    <div>博客描述编写</div>
                    <label>
                        <textarea name="overview"  cols="30" rows="5" placeholder="请编辑博客描述..."></textarea>
                    </label>
                </div>

                <div align="right" style="margin-right: 1em">
                    <div class="ui checkbox">
                        <input type="checkbox" id="top" name="top" class="hidden">
                        <label for="top">置顶</label>
                    </div>
                    &emsp;
                    <div class="ui checkbox">
                        <input type="checkbox" id="comment" name="comment" class="hidden">
                        <label for="comment">开启评论</label>
                    </div>
                </div>

                <div class="ui right aligned container">
                    <button type="button" class="ui button" onclick="window.history.go(-1)">返回</button>
                    <button type="button" id="save-btn" class="ui secondary button">保存</button>
                    <button type="button" id="publish-btn" class="ui blue button">发布</button>
                </div>
            </form>
        </div>
    </UserContentFieldCom>
</template>

<script>
import UserContentFieldCom from "@/components/UserContentFieldCom";
import {ref} from "vue";
export default {
    name: "UserBlogInputView",
    components: {UserContentFieldCom,},
    setup() {
        const error_message = ref("操作错误");

        const close_error_message = () => {
            error_message.value = "";
        }

        return {
            error_message,
            close_error_message
        }

    }
}
</script>

<style scoped>

</style>