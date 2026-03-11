<template>
  <div class="app">
    <header>
      <h1>学生信息管理系统</h1>
      <nav>
        <router-link to="/student">学生管理</router-link>
        <router-link to="/user">用户管理</router-link>
        <router-link to="/college">学院管理</router-link>
        <router-link to="/major">专业管理</router-link>
        <router-link to="/role">角色管理</router-link>
      </nav>
    </header>
    <main>
      <router-view></router-view>
    </main>
  </div>
</template>

<style>
/* 全局样式 */
* {
  margin: 0;
  padding: 0;
  box-sizing: border-box;
}

body {
  font-family: Arial, sans-serif;
  color: #333;
}

.app {
  width: 100%;
  min-height: 100vh;
}

header {
  background-color: rgba(255, 255, 255, 0.9);
  padding: 20px;
  box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
  position: sticky;
  top: 0;
  z-index: 100;
  backdrop-filter: blur(10px);
}

h1 {
  margin: 0 0 20px 0;
  color: #333;
  text-align: center;
  font-size: 24px;
}

nav {
  display: flex;
  gap: 15px;
  justify-content: center;
  flex-wrap: wrap;
}

nav a {
  text-decoration: none;
  color: #333;
  padding: 8px 16px;
  border-radius: 20px;
  background-color: #f8f9fa;
  transition: all 0.3s ease;
  font-weight: 500;
  border: 1px solid #e9ecef;
}

nav a:hover {
  background-color: #e9ecef;
  transform: translateY(-2px);
  box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
}

nav a.router-link-active {
  background: linear-gradient(135deg, #4f46e5, #4338ca);
  color: white;
  border: none;
  box-shadow: 0 4px 12px rgba(79, 70, 229, 0.4);
}

main {
  padding: 0;
  min-height: calc(100vh - 120px);
}
</style>