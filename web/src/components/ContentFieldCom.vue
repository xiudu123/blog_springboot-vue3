<template>
<NavbarCom/>

    <div id="main">
        <div class="ui container">
            <slot></slot>
        </div>
    </div>

<ToolbarCom/>
<FooterCom/>
</template>

<script>
import NavbarCom from "@/components/NavbarCom";
import ToolbarCom from "@/components/ToolbarCom";
import FooterCom from "@/components/FooterCom";
export default {
    name: "ContentFieldCom",
    components: {FooterCom, ToolbarCom, NavbarCom},
}
</script>

<style scoped>
#main {
    min-height: 70vh;
    margin-top: 5em;
    padding-top: 3em;
    padding-bottom: 2em;
}
</style>