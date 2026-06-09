<template>
    <div class="min-h-screen bg-app-background py-4 px-2 md:p-8">
        <div class="max-w-3xl mx-auto bg-card-background backdrop-blur-sm p-6 md:p-10 rounded-2xl border border-app-border shadow-xl space-y-8">

            <!-- Header -->
            <div class="border-b border-app-border/80 pb-6">
                <h1 class="text-3xl md:text-4xl w-fit font-black bg-linear-to-r from-primary-indigo to-primary-emerald bg-clip-text text-transparent mb-2">
                    Privacy Policy
                </h1>
                <p class="text-xs text-app-text-secondary font-medium">Effective Date: June 2026</p>
            </div>

            <!-- Introduction -->
            <p class="text-sm md:text-base leading-relaxed">
                Welcome to <strong>TM-Maple</strong> ("we", "our", or "the Website"). We respect your privacy and are committed to protecting any personal data collected through your use of our service. This Privacy Policy explains what data we collect, how it is stored, and your rights regarding this information.
            </p>

            <!-- Section 1 -->
            <section class="space-y-4">
                <h2 class="text-xl font-bold flex items-center gap-2">
                    <span class="text-primary-indigo">1. Data Collection and Usage</span>
                </h2>
                <p class="text-sm leading-relaxed">
                    We offer two modes of interaction: Guest Mode and Logged-In Mode (via Discord).
                </p>

                <!-- Sub section A (Guest Mode) -->
                <div class="bg-subcard-background rounded-xl p-4 border border-app-border/20 space-y-2">
                    <h3 class="text-sm font-bold text-primary-indigo uppercase tracking-wider">A. Guest Mode (No Authentication)</h3>
                    <p class="text-sm leading-relaxed">
                        If you play as a guest, we do not store any personal data on our backend servers. However, to provide a consistent user experience, we store the following technical data locally in your browser's <span class="font-semibold">LocalStorage</span>:
                    </p>
                    <ul class="list-disc list-inside text-xs text-app-text-secondary space-y-1 pl-2">
                        <li><strong>Gameplay data:</strong> Your daily map attempts for each game mode.</li>
                        <li><strong>User preferences:</strong> Your chosen theme (e.g., Dark Mode) and statistics filter preferences.</li>
                    </ul>
                </div>

                <!-- Sub section B (Logged-In Mode) -->
                <div class="bg-subcard-background rounded-xl p-4 border border-app-border/20 space-y-2">
                    <h3 class="text-sm font-bold text-primary-emerald uppercase tracking-wider">B. Logged-In Mode (Discord Authentication)</h3>
                    <p class="text-sm leading-relaxed">
                        When you choose to sign in using your Discord account, we collect specific information provided via Discord OAuth2 to create and maintain your profile:
                    </p>
                    <ul class="list-disc list-inside text-xs text-app-text-secondary space-y-1 pl-2">
                        <li><strong>Account Identifiers:</strong> Your unique Discord ID, Username, and Avatar hash/URL.</li>
                        <li><strong>Authentication Data:</strong> A secure JWT (JSON Web Token) is stored in your browser's LocalStorage to keep you securely logged in.</li>
                        <li><strong>Gameplay and Progress:</strong> Your scores are saved in our database to generate your personal statistics dashboard.</li>
                    </ul>
                </div>
            </section>

            <!-- Section 2 -->
            <section class="space-y-3">
                <h2 class="text-xl font-bold text-white flex items-center gap-2">
                    <span class="text-primary-indigo">2. Cookies and Local Storage</span>
                </h2>
                <p class="text-sm leading-relaxed">
                    We do not use tracking or marketing cookies. We strictly use browser LocalStorage for essential functionalities (maintaining your session token and saving your daily progression).
                </p>
            </section>

            <!-- Section 3 -->
            <section class="space-y-3">
                <h2 class="text-xl font-bold flex items-center gap-2">
                    <span class="text-primary-indigo">3. Data Retention and Sharing</span>
                </h2>
                <p class="text-sm leading-relaxed">
                    <strong>Retention:</strong> We retain your account data and statistics as long as your account remains active. Local data in your browser remains until the next day or until you clear your browser cache.
                </p>
                <p class="text-sm leading-relaxed">
                    <strong>Sharing:</strong> We do not sell, rent, or share your data with any third-party companies. Your Discord data is fetched strictly through the official Discord API.
                </p>
            </section>

            <!-- Section 4 -->
            <section class="space-y-3">
                <h2 class="text-xl font-bold flex items-center gap-2">
                    <span class="text-primary-indigo">4. Your Rights (GDPR Compliance)</span>
                </h2>
                <p class="text-sm leading-relaxed">
                    If you reside in the European Union or other jurisdictions with applicable privacy laws, you have the right to access, rectify, or request the permanent deletion of all your personal data stored in our database.
                </p>
                <p class="text-sm mt-2">
                    To request data deletion, or if you have any question, please contact us on Discord: <span class="text-discord select-all">@arsolight</span>
                </p>
            </section>

            <!-- Section 5 -->
            <section class="space-y-3 border-t border-app-border/80 pt-6 text-center">
                <h2 class="text-lg font-bold">Changes to This Policy</h2>
                <p class="text-xs text-app-text-secondary max-w-xl mx-auto">
                    We reserve the right to modify this Privacy Policy at any time. Any updates will be posted on this page with an updated effective date.
                </p>
            </section>

        </div>
    </div>
</template>
