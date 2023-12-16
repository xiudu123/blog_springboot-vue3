<template>

    <div style="opacity: 0">1</div>
    <div style="opacity: 0">1</div>
    <div style="opacity: 0">1</div>
    <div id="main" class="m-margin-top-large">
        <div class="ui container">
            <slot></slot>
        </div>
    </div>



</template>

<script>

export default {
    name: "ContentFieldCom",

}
</script>

<style scoped>
#main {
    min-height: 70vh !important;
}
</style>